# Agent Working Agreement

This file is for any developer agent (Codex, Copilot, etc.) working in this repo.
The goal is to keep everyone aligned even when different agents are used.

## 반드시 읽기 (매 작업 전)
- `AGENT/docs/01-folder-architecture.md`
- `AGENT/docs/02-specs.md`
- `AGENT/docs/03-product-plan.md`
- `AGENT/docs/todo/00-todo-list.md`

## 작업 시작 체크리스트
- 위 필수 문서를 읽었는가
- `AGENT/docs/todo/00-todo-list.md`를 확인했는가
- 작업이 [사방에서 몰려오는 오크들을 사냥해 살아남는 탑뷰 뱀서라이크 게임]을 벗어나지 않는가
- 폴더 구조와 [유니티 표준 컴포넌트 기반 아키텍처] 패턴 규칙을 지키는가
- API 사용은 [UnityEngine API 및 팀 컨벤션]만 사용하는가
- UI/스타일링은 [Canvas, TextMeshPro 및 Filled Image HP 게이지] 방식인가
- 현재 작업과 관련된 TODO가 있으면 해당 `AGENT/docs/todo/*.md`를 읽고, 내용을 사용자에게 먼저 알린 뒤 이번 요청 범위에 함께 반영할지 확인했는가

## 코드 변경 규칙 (요약)
- 물리 연산 및 움직임 제어는 `FixedUpdate`를 이용하고, `Time.fixedDeltaTime`을 적용한다.
- 플레이어의 입력 감지(마우스 클릭, 키보드 입력 등)는 `Update`에서 수행한다.
- 하드코딩된 값(속도, 주기 등)은 가급적 Inspector에서 조절 가능하도록 `public` 혹은 `[SerializeField]`로 노출한다.
- 씬 내 싱글톤 인스턴스는 `GameManager.instance`를 통해 통일하여 접근한다.

## 문서 업데이트 규칙
- 구조/스펙/기획 변경 시 해당 문서(01/02/03)를 반드시 업데이트
- 이번 작업이 문서 내용과 다르면 먼저 문서를 갱신한 뒤 코드 수정
- 중대한 변경사항/기록 필요 작업은 `AGENT/docs/reports`에 `yymmdd-HHMM-NN-작업키워드.md` 형식으로 기록
- 템플릿: `AGENT/docs/reports/_template.md`
- 현재 바로 처리하지 못하지만 추후 반드시 진행해야 하는 작업이 생기면 `AGENT/docs/todo`에 TODO 문서를 추가
- TODO 추가 시 `AGENT/docs/todo/00-todo-list.md`에 한 줄 요약도 반드시 함께 갱신
- TODO 완료 시 해당 TODO 파일을 삭제하고, `AGENT/docs/reports/`에 작업 기록을 남긴 뒤, `AGENT/docs/todo/00-todo-list.md`에서도 제거
- TODO와 관련된 요청을 받으면 우선 `AGENT/docs/todo/00-todo-list.md`를 확인하고, 관련 항목이 있으면 해당 TODO 문서를 읽은 뒤 사용자에게 관련 TODO 존재 여부와 반영 범위를 먼저 알릴 것
- 관련 TODO가 현재 요청과 이어질 수 있으면, TODO 내용을 짧게 요약한 뒤 이번 작업에 함께 반영할지 사용자에게 먼저 물어보고 답변을 받은 후 진행할 것
- 사용자가 범위를 분리해 달라고 하면 현재 요청 범위만 처리하고, 관련 TODO는 건드리지 않을 것

## 참고
- 이 문서는 에이전트용이므로 간결하게 유지
- 필요 시 여기에 규칙을 추가하되 01/02/03의 내용과 충돌하면 01/02/03을 우선
