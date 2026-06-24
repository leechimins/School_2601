using UnityEngine;

public class ArrowController : MonoBehaviour
{
    public float speed;
    public float fireDelay;
    Vector3 moveDirection;

    void Start()
    {
        speed = 5.0f;
        Destroy(gameObject, 3f);
    }

    void Update()
    {
        transform.Translate(Vector3.right * speed * Time.deltaTime);

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
            Destroy(gameObject);
        }
    }

    public void Init(Vector2 dir)
    {
        moveDirection = new Vector3(dir.x, dir.y, 0).normalized;
        float angle = Mathf.Atan2(moveDirection.y, moveDirection.x) * Mathf.Rad2Deg;
        transform.rotation = Quaternion.AngleAxis(angle, Vector3.forward);
    }
}
