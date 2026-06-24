using UnityEngine;

public class OrcController : MonoBehaviour
{
    public float speed;
    public Rigidbody2D target;

    Rigidbody2D rigid;
    SpriteRenderer spriter;

    public GameObject dropItemPrefab;
    public float dropRate;

    void Start()
    {
        rigid = GetComponent<Rigidbody2D>();
        spriter = GetComponent<SpriteRenderer>();

        speed = GameManager.instance.orcSpeed;
        dropRate = 0.3f;

        if (target == null)
        {
            if (GameManager.instance != null)
            {
                target = GameManager.instance.player.GetComponent<Rigidbody2D>();
            }
        }
    }

    void FixedUpdate()
    {
        Vector2 dirVec = target.position - rigid.position;
        Vector2 nextVec = dirVec.normalized * speed * Time.fixedDeltaTime;
        rigid.MovePosition(rigid.position + nextVec);
    }

    void LateUpdate()
    {
        spriter.flipX = target.position.x < rigid.position.x;
    }

    public void Die()
    {
        if (Random.value <= dropRate)
        {
            Instantiate(dropItemPrefab, transform.position, Quaternion.identity);
        }
        Destroy(gameObject);
    }

}