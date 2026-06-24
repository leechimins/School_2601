using UnityEngine;
using UnityEngine.UI;
using System.Collections;

public class PlayerController : MonoBehaviour
{
    public float speed;
    public float fireDelay;
    public GameObject arrowPrefab;
    public float maxHp;
    public Image hpBarImage;

    Vector2 inputVec;
    float fireTimer;
    Rigidbody2D rigid;
    SpriteRenderer spriter;
    Animator anim;
    float currentHp;
    float invincibleDuration;
    bool isInvincible = false;

    void Start()    // awake
    {
        rigid = GetComponent<Rigidbody2D>();
        spriter = GetComponent<SpriteRenderer>();
        anim = GetComponent<Animator>();

        speed = 3.0f;
        fireDelay = 0.5f;
        maxHp = 100f;
        invincibleDuration = 0.5f;
        currentHp = maxHp;
    }

    void Update()
    {
        fireTimer += Time.deltaTime;

        inputVec.x = Input.GetAxisRaw("Horizontal");
        inputVec.y = Input.GetAxisRaw("Vertical");

        if (Input.GetMouseButtonDown(0) && fireTimer >= fireDelay)
        {
            Vector2 clickVec = Camera.main.ScreenToWorldPoint(Input.mousePosition);
            Vector2 playerPos = transform.position;
            Vector2 shootDir = (clickVec - playerPos).normalized;
            FireArrow(shootDir);
            fireTimer = 0f;
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
        if (other.gameObject.CompareTag("Orc"))
        {
            DecreaseHp(10f);
            Destroy(other.gameObject);
        }
    }

    void FireArrow(Vector2 dir)
    {
        GameObject arrow = Instantiate(arrowPrefab, transform.position, Quaternion.identity);
        ArrowController arrowController = arrow.GetComponent<ArrowController>();
        arrowController.Init(dir);
    }

    public void DecreaseHp(float amount)
    {
        if (isInvincible) return;

        currentHp -= amount;
        if (currentHp < 0) currentHp = 0;

        UpdateHpUI();

        if (currentHp <= 0)
        {
            GameManager.instance.GameOver();
            GameDirector.instance.DisplayGameOver();
        }
        else
        {
            StartCoroutine(TriggerInvincibility());
        }
    }

    public void IncreaseHp(float amount)
    {
        currentHp += amount;
        if (currentHp > maxHp) currentHp = maxHp;

        UpdateHpUI();
    }

    private void UpdateHpUI()
    {
        hpBarImage.fillAmount = currentHp / maxHp;
    }

    private IEnumerator TriggerInvincibility()
    {
        isInvincible = true;

        anim.SetTrigger("Damaged");
        yield return new WaitForSeconds(invincibleDuration);
        isInvincible = false;
    }
}