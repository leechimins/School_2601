# 1) 폴더 아키텍처

본 문서는 **오크와 궁수** Unity 2D 프로젝트의 폴더 아키텍처를 정의합니다.

---

## A. Top-level 구조

```txt
root
├─ AGENT                                 # ✅ AI 에이전트 컨텍스트 폴더
│  ├─ docs
│  │  ├─ 01-folder-architecture.md
│  │  ├─ 02-specs.md
│  │  ├─ 03-product-plan.md
│  │  ├─ reports
│  │  │  └─ _template.md
│  │  └─ todo
│  │     ├─ _template.md
│  │     └─ 00-todo-list.md
│  └─ VR 게임 기획안.md
├─ AGENTS.md
├─ Assets                                # Unity 프로젝트 루트 에셋
│  ├─ Animations                         # 캐릭터 및 적 애니메이션 컨트롤러, 클립
│  ├─ Prefabs                            # 프리팹 (Player, Orc, Arrow, DropItem 등)
│  ├─ Scenes                             # 게임 씬 파일
│  ├─ Scripts                            # 게임 로직 C# 스크립트
│  │  ├─ GameManager.cs
│  │  ├─ PlayerController.cs
│  │  ├─ OrcController.cs
│  │  ├─ OrcGenerator.cs
│  │  └─ ArrowController.cs (추가 예정)
│  └─ Sprites                            # 2D 그래픽 에셋(이미지, 스프라이트)
├─ ProjectSettings                       # 유니티 프로젝트 설정
└─ Packages                              # 패키지 매니저 종속성
```

- `AGENT/docs/reports`는 완료되었거나 기록이 필요한 작업 내역을 남기는 용도입니다.
- `AGENT/docs/todo`는 선행 작업이나 정책 확정 등이 필요한 후속 작업을 남기는 용도입니다.

---

## B. 스크립트 작성 규칙

- **Scripts/** 폴더 하위에 모든 핵심 로직 코드를 관리합니다.
- 각 스크립트는 단일 책임 원칙에 따라 역할을 분리합니다:
  - `Controller` 접미사: 특정 게임 오브젝트의 이동, 물리, 충돌, 입력을 담당 (예: `PlayerController`, `OrcController`, `ArrowController`)
  - `Generator` 접미사: 프리팹의 동적 생성을 담당 (예: `OrcGenerator`, `ItemGenerator`)
  - `Manager` / `Director` 접미사: 게임 내 전역 상태 및 UI 생명주기 관리 (예: `GameManager`, `GameDirector`)
