# 2) Specs

본 문서는 실제 구현에 사용할 기술 스펙과 운영 규칙을 정의합니다.

---

## A. Runtime / Core

- **엔진**: Unity 2D (버전: 프로젝트 설정 준수)
- **언어**: C#
- **물리 엔진**: Physics 2D (Rigidbody 2D, Collider 2D)
- **UI**: Unity UI (Canvas, Image, TextMeshPro)

---

## B. 게임 메커니즘 설계

### 1) 플레이어 (Player)
- 이동: `Rigidbody2D.MovePosition`을 활용한 8방향 2D 탑다운 물리 이동
- 애니메이션: 속도 크기(`inputVec.magnitude`)에 기반한 Animator 연동
- 공격: 마우스 클릭 방향 계산 후 화살(Arrow) 인스턴스화

### 2) 적 (Orc)
- 추적: 실시간으로 플레이어(`GameManager.instance.player`)의 위치를 타겟팅하여 이동
- 스폰: 화면 외곽 랜덤 경계 좌표에서 동적 무한 스폰 (`OrcGenerator`)

### 3) 투사체 (Arrow)
- 이동: 물리 연산을 사용하지 않고 `transform.Translate`를 사용해 매 프레임 등속 직선 고속 이동
- 소멸: 화면을 벗어나거나, 혹은 발사 후 일정 시간(3초)이 경과하면 소멸

---

## C. 구현 규칙

- 입력 감지(Input Get)는 `Update()`에서, 이동 및 물리적 위치 적용은 `FixedUpdate()`에서 수행합니다.
- 시간 측정 시 `Time.deltaTime` 또는 `Time.fixedDeltaTime`을 각 프레임 방식에 맞게 올바르게 곱해줍니다.
- 프레임 독립성을 보장하고, 하드코딩 속도를 가급적 피하고 Inspector 필드로 조절 가능하게 구성합니다.
