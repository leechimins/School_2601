using UnityEngine;

public class OrcController : MonoBehaviour
{
    public float speed;
    public Rigidbody2D target;

    bool isLive;
    Rigidbody2D rigid;
    SpriteRenderer spriter;

    void Start()
    {
        rigid = GetComponent<Rigidbody2D>();
        spriter = GetComponent<SpriteRenderer>();

        speed = 2.5f;
        isLive = true;

        if (target == null)
        {
            target = GameManager.instance.player.GetComponent<Rigidbody2D>();
        }
    }

    void FixedUpdate()
    {
        if (!isLive || target == null) return;

        Vector2 dirVec = target.position - rigid.position;
        Vector2 nextVec = dirVec.normalized * speed * Time.fixedDeltaTime;
        rigid.MovePosition(rigid.position + nextVec);
    }

    void LateUpdate()
    {
        if (!isLive || target == null) return;

        spriter.flipX = target.position.x < rigid.position.x;
    }
}