using UnityEngine;

public class PlayerController : MonoBehaviour
{
    Vector2 inputVec;
    public float speed;

    Rigidbody2D rigid;
    SpriteRenderer spriteRd;

    void Start()    // awake
    {
        speed = 2.0f;
        rigid = GetComponent<Rigidbody2D>();
        spriteRd = GetComponent<SpriteRenderer>();
    }

    void Update()
    {
        inputVec.x = Input.GetAxisRaw("Horizontal");
        inputVec.y = Input.GetAxisRaw("Vertical");
    }

    void FixedUpdate()
    {
        Vector2 nextVec = inputVec.normalized * speed * Time.fixedDeltaTime;
        rigid.MovePosition(rigid.position + nextVec);

    }

    void LateUpdate()
    {
        if (inputVec.x != 0) {
            spriteRd.flipX = inputVec.x < 0;
        }
    }
}
