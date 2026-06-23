# TODO: 체력(HP) 시스템 및 UI 연동

## 목적
- 플레이어의 체력을 체계적으로 관리하고, UI HP 게이지에 실시간 반영

## 세부 태스크
1. **플레이어 HP 변수 선언 및 관리**
   - `PlayerController.cs`에 최대 체력(Max HP) 및 현재 체력(Current HP) 변수 설계.
2. **피격 시 HP 감산 및 무적 시간 적용**
   - 오크와 충돌 시 오크는 소멸하고 플레이어 HP 감산 (`DecreaseHp` 메서드 호출).
   - 피격 시 짧은 무적 프레임(예: 0.5초)을 두어 다단 히트 방지.
3. **UI HP 게이지 갱신**
   - UI Canvas 내 `Image` 컴포넌트(Filled Type)의 `fillAmount` 값을 `CurrentHP / MaxHP` 비율로 실시간 업데이트.
