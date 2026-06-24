# 🏹 오크와 궁수 (Orcs & Archer) - 발표 및 프로젝트 요약서
본 문서는 수업에서 학습한 **5단계 설계법**을 기반으로 본 프로젝트의 아키텍처를 분석하고, **발표 영상 촬영 시 코드와 함께 설명할 수 있도록 구성된 발표 가이드라인**을 포함하고 있습니다.

---

## 🎮 1. 프로젝트 개요
- **장르**: 2D 탑뷰 서바이벌 액션 (Vampire Survivors 라이크)
- **핵심 재미 요소**: 사방에서 쏟아지는 오크들을 화살로 저격하고, 처치 시 드롭되는 체력 물약을 획득해 생존을 도모하며 시간이 흐를수록 극한으로 상승하는 난이도(오크 속도 증가 및 스폰 주기 단축) 속에서 최대한 오래 생존하는 서바이벌 게임입니다.

---

## 📐 2. [강의록 기반] 5단계 설계법 분석

수업에서 학습한 오브젝트 설계 5단계를 본 프로젝트에 대입한 구조는 다음과 같습니다.

### 1단계: 화면에 놓일 오브젝트 나열
- **게임 공간**: 지면(Background), 카메라(Main Camera)
- **플레이어 캐릭터**: 플레이어(Player), 체력바(HP Bar UI)
- **적 캐릭터**: 오크(Orc)
- **동적 투사체**: 화살(Arrow)
- **아이템**: 체력 물약(DropItem)
- **UI 요소**: 생존 시간 텍스트(TimeText), 처치 점수 텍스트(ScoreText), 게임오버 결과창(GameOverPanel)

### 2단계: 오브젝트를 움직일 수 있는 컨트롤러(Controller) 스크립트
- **PlayerController**: 플레이어의 입력 감지, 8방향 물리 이동 제어, 마우스 방향 화살 인스턴스화, HP 관리.
- **OrcController**: 플레이어를 실시간 추적하는 AI 이동 및 사망 시 물약 드롭 처리.
- **ArrowController**: 발사된 화살의 고속 등속 직선 운동 및 화면 밖 이탈 시 파괴 처리.
- **ItemController**: 물약 아이템이 플레이어와 부딪혔을 때 체력을 20 회복시키는 충돌 연동.

### 3단계: 오브젝트를 자동으로 생성하는 제너레이터(Generator) 스크립트
- **OrcGenerator**: 화면 외곽 상/하/좌/우 영역의 좌표를 계산하여 오크를 실시간 무한 생성.

### 4단계: UI 갱신 및 전역 관리를 담당하는 감독(Director / Manager) 스크립트
- **GameManager**: 싱글톤 구조로 게임의 전체 상태(스코어, 생존 시간, 난이도 조절 매개변수, 사망 여부) 보관.
- **GameDirector**: 매 프레임 시간 갱신 UI 반영, 10초 주기 난이도 상승 연산, 게임 오버 시 결과창 시각화.

### 5단계: 스크립트 작성 및 씬 연동 흐름
- 전역 데이터 매니저(`GameManager`) 구축 ➡️ 플레이어 제어 및 체력 시스템 구현 ➡️ 오크 추적 AI 및 스폰 제너레이터 연동 ➡️ 투사체 물리 연동 및 충돌 판정 구현 ➡️ UI 연출 및 게임오버/난이도 조절 감독 스크립트 작성.

---

## 🎤 3. 발표 영상용 코드 설명 시나리오 & 핵심 코드

발표 영상에서 코드를 띄우고 설명할 때 활용할 수 있는 **핵심 코드 블록과 설명 멘트(대본)**입니다.

---

### ① GameManager.cs - "싱글톤과 전역 상태 관리"
> **발표 멘트**:  
> *"먼저 게임의 전체 데이터 상태와 전역 제어를 총괄하는 `GameManager`입니다. 씬 내의 어떤 스크립트에서도 쉽게 상태 값에 접근할 수 있도록 **싱글톤(Singleton) 패턴**을 구현하여 `GameManager.instance`로 통일되게 접근합니다. 게임오버 상태 플래그(`isGameOver`) 및 현재 난이도 속성(`orcSpeed`, `orcSpawnSpan`)을 보관하고 관리합니다."*

```csharp
public static GameManager instance;

private void Awake()
{
    instance = this; // 싱글톤 인스턴스 할당
    // 게임 리셋 초기화
    score = 0;
    survivalTime = 0f;
    isGameOver = false;
}

public void GameOver()
{
    if (isGameOver) return;
    isGameOver = true;
    Time.timeScale = 0f; // 게임 내 물리/시간 흐름 완전 정지
}
```

---

### ② PlayerController.cs - "물리 이동과 무적 처리 코루틴"
> **발표 멘트**:  
> *"플레이어의 물리 연산 및 이동 흐름입니다. 유니티 권장 컨벤션에 맞춰 키보드 입력 값 감지(`GetAxisRaw`)는 `Update`에서 매 프레임 확인하고, 물리적 이동은 프레임과 독립적인 `FixedUpdate` 내에서 `MovePosition`을 활용해 8방향 2D 물리 이동을 구현했습니다. 또한 피격 시 0.5초 동안 무적이 되는 코루틴 함수(`TriggerInvincibility`)를 두어 피격 시 깜빡이는 무적 상태를 연출했습니다."*

```csharp
void FixedUpdate()
{
    // 입력 벡터에 따른 8방향 등속 물리 이동 처리
    Vector2 nextVec = inputVec.normalized * speed * Time.fixedDeltaTime;
    rigid.MovePosition(rigid.position + nextVec);
}

private IEnumerator TriggerInvincibility()
{
    isInvincible = true;
    anim.SetTrigger("Damaged"); // 피격 애니메이션 트리거
    yield return new WaitForSeconds(invincibleDuration);
    isInvincible = false; // 무적 시간 해제
}
```

---

### ③ OrcController.cs - "플레이어 타겟 추적 및 드롭 아이템 연동"
> **발표 멘트**:  
> *"오크는 스폰되자마자 싱글톤으로 제공되는 `GameManager.instance.player`의 좌표를 실시간 타겟팅하여 추적합니다. 오크가 화살에 맞아 소멸하는 `Die()` 메서드에서는 하드코딩을 배제하고 Inspector에서 조절 가능한 드롭 확률(`dropRate`, 기본 30%)에 따라 체력 물약 프리팹을 동적으로 스폰하도록 제작했습니다."*

```csharp
void FixedUpdate()
{
    // 플레이어의 Rigidbody2D 위치를 바라보고 추적 물리 이동
    Vector2 dirVec = target.position - rigid.position;
    Vector2 nextVec = dirVec.normalized * speed * Time.fixedDeltaTime;
    rigid.MovePosition(rigid.position + nextVec);
}

public void Die()
{
    if (Random.value <= dropRate) // 30% 확률로 아이템 드롭
    {
        Instantiate(dropItemPrefab, transform.position, Quaternion.identity);
    }
    Destroy(gameObject);
}
```

---

### ④ OrcGenerator.cs - "동적 4방향 외곽 영역 스폰"
> **발표 멘트**:  
> *"오크 제너레이터는 플레이어의 시야 밖 외곽 경계에서 몬스터를 계속 스폰합니다. `FixedUpdate`에서 `GameManager`에 실시간으로 업데이트되는 `orcSpawnSpan`을 바라보며 스폰 주기를 계산합니다. 0~3까지의 랜덤 값을 switch-case 문으로 매핑하여 화면의 상/하/좌/우 외곽 랜덤 좌표에 오크를 인스턴스화합니다."*

```csharp
void FixedUpdate()
{
    span = GameManager.instance.orcSpawnSpan; // 난이도에 따른 동적 주기 반영
    delta += Time.fixedDeltaTime;
    if (delta > span)
    {
        delta = 0;
        SpawnOrc();
    }
}
```

---

### ⑤ ArrowController.cs - "투사체 이동 및 화면 이탈 소멸 처리"
> **발표 멘트**:  
> *"화살은 마우스 클릭 방향으로 회전(`Init`)된 후, 매 프레임 `transform.Translate`를 통해 등속 직선 운동을 수행합니다. 3초가 지나면 자동 파괴되도록 수명을 두었으며, 뷰포트 좌표를 기준으로 화면 밖으로 나가는 즉시 파괴되도록 하여 불필요한 메모리 누수를 원천 차단했습니다. 또한 충돌 시 오크를 처치하며 킬 스코어를 누적시킵니다."*

```csharp
void Update()
{
    transform.Translate(Vector3.right * speed * Time.deltaTime);

    // 화면 밖으로 나갔는지 뷰포트 비율로 검사하여 파괴
    Vector3 viewPos = Camera.main.WorldToViewportPoint(transform.position);
    if (viewPos.x < 0f || viewPos.x > 1f || viewPos.y < 0f || viewPos.y > 1f)
    {
        Destroy(gameObject);
    }
}

void OnTriggerEnter2D(Collider2D other)
{
    if (other.CompareTag("Orc"))
    {
        OrcController orc = other.GetComponent<OrcController>();
        orc.Die();
        GameManager.instance.AddScore(1); // 점수 추가
        Destroy(gameObject);
    }
}
```

---

### ⑥ GameDirector.cs - "실시간 시간 갱신 및 10초 주기 웨이브 난이도 상승"
> **발표 멘트**:  
> *"마지막으로 게임을 연출하는 감독 스크립트인 `GameDirector`입니다. 매 프레임 생존 시간을 갱신해 TextMeshPro UI에 전달하며, 난이도 타이머가 10초에 도달할 때마다 웨이브 레벨을 올리고 오크 속도를 높이거나 스폰 딜레이를 좁혀 동적 웨이브 긴장감을 형성합니다. 또한 플레이어가 사망해 GameManager에서 게임오버가 감지되면 어두운 팝업 결과창을 활성화해 최종 기록을 보여줍니다."*

```csharp
private void Update()
{
    if (GameManager.instance.isGameOver) return;

    GameManager.instance.survivalTime += Time.deltaTime;
    difficultyTimer += Time.deltaTime;

    timeText.text = $"Time: {GameManager.instance.survivalTime:F1}s";
    scoreText.text = $"Score: {GameManager.instance.score}";

    if (difficultyTimer >= 10f) // 10초마다 난이도 상승
    {
        difficultyTimer = 0f;
        IncreaseDifficulty();
    }
}

private void IncreaseDifficulty()
{
    GameManager.instance.waveLevel++;
    GameManager.instance.orcSpeed += 0.5f; // 오크 속도 상승
    // 스폰 딜레이 단축 (최대 속도 0.2초 한계점 지정)
    GameManager.instance.orcSpawnSpan = Mathf.Max(0.2f, GameManager.instance.orcSpawnSpan - 0.15f);
}
```

---

## 🎬 4. 발표 영상 녹화 팁
1. **시연 우선**: 게임을 먼저 가볍게 10~20초 정도 플레이하여, 캐릭터가 움직이고 오크가 스폰되어 쫓아오며, 시간이 지나면 점점 빨라지다가 죽었을 때 게임 오버 창이 뜨는 실제 동작 방식을 보여주세요.
2. **5단계 설계법에 따른 코드 설명**: 시연 후, *"이 게임은 수업에서 배운 5단계 설계법에 맞춰 스크립트가 체계화되어 있습니다"* 라고 말하며 `Controller` ➡️ `Generator` ➡️ `Director / Manager` 순으로 코드를 띄워가며 위의 핵심 블록을 짚으세요.
3. **규칙 강조**: `Update`와 `FixedUpdate`를 분리해 입력을 감지하고 물리 위치를 제어한 부분, 전역 상태를 싱글톤인 `GameManager.instance`로 통일해서 접근한 부분을 수업 연계 포인트로 강조하면 좋은 점수를 받을 수 있습니다.
