# 작업 기록 - 게임 감독/게임 오버 및 동적 난이도 조절 시스템

> 제목 규칙: `작업 기록 - {작업명}`  
> 파일명 권장: `yymmdd-HHMM-NN-작업키워드.md`  

- 일시: 2026-06-24 22:34 (KST)
- 작성자: USER
- 에이전트: Gemini (Antigravity)
- 작업 유형: 기능 추가

## 요약
- **게임 감독(GameDirector)** 시스템을 구축하여 실시간 게임 시간 및 오크 처치 점수를 연산하고 화면 상단 UI에 반영하였습니다.
- 플레이어 체력이 0 이하가 될 때 게임 루프를 일시정지(`Time.timeScale = 0`)시키고 화면 중앙에 최종 기록 결과 팝업창을 띄우는 **게임 오버 판정**을 구현하였습니다.
- 30초마다 난이도 레벨(Wave Level)이 상승하여 오크의 속도가 빨라지고 스폰 주기가 촘촘해지는 **동적 난이도 조절(웨이브 시스템)**을 구현하였습니다.

## 변경 범위
- **GameManager**: 실시간 스코어, 생존 시간, 게임 오버 상태 및 난이도 설정값(오크 속도, 스폰 주기)을 보관하고 싱글톤 접근을 지원합니다.
- **GameDirector**: 실시간 UI 텍스트 갱신, 30초 난이도 증가 처리, 게임 오버 시 UI 팝업 노출을 제어합니다.
- **PlayerController**: 사망 판정 시 `GameManager`와 `GameDirector`를 연동하여 게임 오버 처리를 트리거합니다.
- **OrcController & OrcGenerator**: 스폰될 때 `GameManager`에 등록된 동적 속도 및 스폰 딜레이를 바라보도록 구조를 수정하였습니다.
- **ArrowController**: 투사체 충돌 처치 시 `GameManager.instance.AddScore(1)`을 통해 점수가 가산되도록 수정하였습니다.

## 주요 변경 파일
- [GameManager.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/GameManager.cs)
- [GameDirector.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/GameDirector.cs) [NEW]
- [PlayerController.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/PlayerController.cs)
- [OrcController.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/OrcController.cs)
- [OrcGenerator.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/OrcGenerator.cs)
- [ArrowController.cs](file:///d:/School/2026_1_Git/VRAR/Final_Project/Assets/Scripts/ArrowController.cs)

## 리스크/이슈
- **재시작 기능 제외**: 기획 검토 과정에서 재시작 기능은 구현하지 않기로 결정하여 결과창의 다시 시작 버튼 배치 및 관련 씬 관리 로직은 배제되었습니다.
- **타임스케일 리셋**: 추후 씬 전환이나 리로드 로직이 추가될 경우를 대비해 `GameDirector.Start()`에서 `Time.timeScale = 1.0f`로 시간 흐름을 보장하도록 방어 코드를 적용해 두었습니다.

## 다음 작업
- UI 레이아웃 미세 정렬 및 폰트 변경(SDF 적용)에 따른 가독성 확보.
- 기획안의 파티클 이펙트 폭발 연출(후순위 보류된 건) 및 드롭 물약의 밸런싱 테스트.

## 참고
- 관련 문서: [04-game-director-gameover.md](file:///d:/School/2026_1_Git/VRAR/Final_Project/AGENT/docs/todo/04-game-director-gameover.md) (삭제됨), [05-dynamic-difficulty.md](file:///d:/School/2026_1_Git/VRAR/Final_Project/AGENT/docs/todo/05-dynamic-difficulty.md) (삭제됨)
