# 🏹 오크와 궁수 (Orcs & Archer) - 발표 및 프로젝트 요약서
본 문서는 수업에서 학습한 **5단계 설계법**을 기반으로 본 프로젝트의 아키텍처를 분석하고, **발표 영상 촬영 시 코드와 함께 설명할 수 있도록 구성된 발표 가이드라인**을 포함하고 있습니다.

---

## 🎮 1. 프로젝트 개요
- **장르**: 2D 탑뷰 서바이벌 액션 (Vampire Survivors 라이크)
- **핵심 재미 요소**: 사방에서 쏟아지는 오크들을 화살로 저격하고, 처치 시 드롭되는 체력 물약을 획득해 생존을 도모하며 시간이 흐를수록 극한으로 상승하는 난이도(오크 속도 증가 및 스폰 주기 단축) 속에서 최대한 오래 생존하는 서바이벌 게임입니다.
- **심화 연동**: 플레이어 사망 시 단순 UI 활성화가 아닌, 독립된 결과창 씬(`GameOverScene`)으로 화면이 전환되며, 이전 씬의 점수와 시간 데이터를 유지한 채 결과 텍스트를 출력하고 재시작을 지원합니다.

---

## 📐 2. [강의록 기반] 5단계 설계법 분석

수업에서 학습한 오브젝트 설계 5단계를 본 프로젝트에 대입한 구조는 다음과 같습니다.

### 1단계: 화면에 놓일 오브젝트 나열
- **게임 공간**: 지면(Background), 카메라(Main Camera), 결과창 씬(GameOverScene)
- **플레이어 캐릭터**: 플레이어(Player), 체력바(HP Bar UI)
- **적 캐릭터**: 오크(Orc)
- **동적 투사체**: 화살(Arrow)
- **아이템**: 체력 물약(DropItem)
- **UI 요소**: 생존 시간 텍스트(TimeText), 처치 점수 텍스트(ScoreText), 결과 씬 텍스트(FinalRecordText)

### 2단계: 오브젝트를 움직일 수 있는 컨트롤러(Controller) 스크립트
- **PlayerController**: 플레이어의 입력 감지, 8방향 물리 이동 제어, 마우스 방향 화살 인스턴스화, HP 관리 및 사망 신호 송출.
- **OrcController**: 플레이어를 실시간 추적하는 AI 이동 및 사망 시 물약 드롭 처리.
- **ArrowController**: 발사된 화살의 고속 등속 직선 운동 및 화면 밖 이탈 시 파괴 처리.
- **ItemController**: 물약 아이템이 플레이어와 부딪혔을 때 체력을 20 회복시키는 충돌 연동.

### 3단계: 오브젝트를 자동으로 생성하는 제너레이터(Generator) 스크립트
- **OrcGenerator**: 화면 외곽 상/하/좌/우 영역의 좌표를 계산하여 오크를 실시간 무한 생성.

### 4단계: UI 갱신 및 전역 관리를 담당하는 감독(Director / Manager) 스크립트
- **GameManager**: 싱글톤 및 `DontDestroyOnLoad` 구조로 씬 전환 시에도 스코어와 시간 데이터를 안전하게 유지 및 초기화 처리.
- **GameDirector**: 인게임 씬 내 UI 텍스트 업데이트 및 10초 주기 동적 난이도 조절.
- **GameOverDirector**: 결과창 씬 내 최종 성적 시각화 및 게임 재시작 흐름 제어.

### 5단계: 스크립트 작성 및 씬 연동 흐름
- 전역 데이터 매니저(`GameManager`) 구축 ➡️ 씬 전환 데이터 보존 및 예외 방어 구현 ➡️ 플레이어 제어 및 체력 시스템 구현 ➡️ 오크 추적 AI 및 스폰 제너레이터 연동 ➡️ 투사체 물리 연동 및 충돌 판정 구현 ➡️ 인게임 및 결과창 감독 스크립트 작성 ➡️ Build Settings 등록.

---

## 📁 3. 스크립트 아키텍처 및 역할

모든 스크립트는 `Assets/Scripts/` 폴더 내에 단일 책임 원칙에 따라 분리하여 설계되었습니다.

1. **[GameManager.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/GameManager.cs)**
   - 게임의 전역 상태(점수, 시간, 난이도 계수, 게임오버 상태)를 관리하는 싱글톤 인스턴스. 씬 전환 시 파괴 방지 및 씬 리로드 시 복제본 중복 생성 제거 방어 코드를 갖추고 있습니다.
2. **[GameDirector.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/GameDirector.cs)**
   - 실시간 UI 텍스트 갱신 및 10초 주기 난이도 상승 트리거를 제어하는 인게임 감독 컴포넌트.
3. **[GameOverDirector.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/GameOverDirector.cs)**
   - 결과 씬에서 보존된 GameManager 데이터를 바탕으로 최종 기록을 표시하고, 재시작 씬 전환을 조율하는 결과창 감독 컴포넌트.
4. **[PlayerController.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/PlayerController.cs)**
   - 플레이어 입력 감지, 8방향 이동 물리 처리, 마우스 방향 투사체 발사, HP 관리 및 피격 무적 코루틴 제어.
5. **[OrcController.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/OrcController.cs)**
   - 개별 오크의 타겟 추적 이동 및 사망 시 아이템 드롭 확률 연산.
6. **[OrcGenerator.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/OrcGenerator.cs)**
   - 동적 오크 프리팹 생성기. GameManager의 스폰 주기 변수를 실시간으로 반영하여 스폰 딜레이 조절.
7. **[ArrowController.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/ArrowController.cs)**
   - 화살의 고속 직선 운동, 화면 밖 이탈 시 소멸 처리, 오크 충돌 판정 및 GameManager의 처치 스코어 가산 처리.
8. **[ItemController.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/ItemController.cs)**
   - 물약 아이템의 충돌 감지 및 플레이어 HP 회복 적용.

---

## ⚙️ 4. 유니티 씬(Scene) 계층 구조(Hierarchy) 및 빌드 가이드

### 1) GameScene (인게임 씬)
```txt
Hierarchy
├─ GameManager            # GameManager.cs 부착 (Player 오브젝트 할당)
├─ GameDirector           # GameDirector.cs 부착 (UI Elements 드래그 연동)
├─ Player                 # PlayerController.cs, Rigidbody2D, Collider2D, Animator 부착
├─ Main Camera
├─ Canvas                 # UI 루트
│  ├─ HpBar               # Player HP UI Image (Filled 타입)
│  ├─ TimeText            # TextMeshPro - 생존 시간 출력 (GameDirector 연동)
│  └─ ScoreText           # TextMeshPro - 처치 스코어 출력 (GameDirector 연동)
└─ OrcGenerator           # OrcGenerator.cs 부착 (오크 프리팹 할당)
```

### 2) GameOverScene (결과창 씬)
```txt
Hierarchy
├─ GameOverDirector       # GameOverDirector.cs 부착 (FinalRecordText 및 Restart Button 연동)
├─ Main Camera
└─ Canvas                 # UI 루트
   ├─ BackgroundPanel     # 어두운 반투명 패널
   ├─ TitleText           # TextMeshPro - "GAME OVER" 타이틀
   ├─ FinalRecordText     # TextMeshPro - "Survival Time: 00s \n Score: 00 Kills" 형식으로 출력
   └─ RestartButton       # Button - OnClick 이벤트로 GameOverDirector.OnClickRestart 등록
```

### 3) 빌드 설정 (Build Settings) 등록 필수
씬 전환이 정상 작동하기 위해 **`File ➡️ Build Settings`** 메뉴를 실행한 뒤, 씬 목록(`Scenes In Build`)에 `GameScene`과 `GameOverScene`을 드래그 앤 드롭으로 모두 추가해 주어야 로딩 시 크래시가 발생하지 않습니다.
