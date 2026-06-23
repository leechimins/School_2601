using UnityEngine;

public class PlayerController : MonoBehaviour
{
    Vector2 inputVec;
    public float speed;

    Rigidbody2D rigid;
    SpriteRenderer spriter;
    Animator anim;

    GameObject arrowPrefab;

    void Start()    // awake
    {
        rigid = GetComponent<Rigidbody2D>();
        spriter = GetComponent<SpriteRenderer>();
        anim = GetComponent<Animator>();

        speed = 3.0f;
    }

    void Update()
    {
        inputVec.x = Input.GetAxisRaw("Horizontal");
        inputVec.y = Input.GetAxisRaw("Vertical");
        if (Input.GetMouseButtonDown(0))
        {
            Vector2 clickVec = Camera.main.ScreenToWorldPoint(Input.mousePosition);
            Vector2 playerPos = transform.position;
            Vector2 shootDir = (clickVec - playerPos).normalized;
            FireArrow(shootDir);
        }
    }

    void FixedUpdate()
    {
        Vector2 nextVec = inputVec.normalized * speed * Time.fixedDeltaTime;
        rigid.MovePosition(rigid.position + nextVec);

    }

    void LateUpdate()
    {
        anim.SetFloat("Speed", inputVec.magnitude);

        if (inputVec.x != 0)
        {
            spriter.flipX = inputVec.x < 0;
        }
    }

    void OnCollisionEnter2D(Collision2D other)
    {
        switch (other.gameObject.tag)
        {
            case "Orc":
                Debug.Log("ÀâÇû´Ù.");
                break;
            case "Item":
                break;
            default:
                return;
        }
        Destroy(other.gameObject);
    }

    void FireArrow(Vector2 dir)
    {
        GameObject arrow = Instantiate(arrowPrefab, transform.position, Quaternion.identity);
        ArrowController arrowController = arrow.GetComponent<ArrowController>();
        arrowController.Init(dir);
    }
}
