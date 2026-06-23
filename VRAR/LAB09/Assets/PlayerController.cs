using UnityEngine;
using UnityEngine.SceneManagement;

public class PlayerController : MonoBehaviour
{
    Rigidbody2D rigid2D;
    float jumpForce = 600.0f;

    float walkForce = 30.0f;
    float maxWalkSpeed = 2.0f;

    public Sprite[] walkSprites;
    public Sprite jumpSprite;

    float time = 0;
    int idx = 0;
    SpriteRenderer spriteRenderer;

    void Start()
    {
        Application.targetFrameRate = 60;
        rigid2D = GetComponent<Rigidbody2D>();
        spriteRenderer = GetComponent<SpriteRenderer>();
    }
    void Update()
    {
        if (rigid2D.linearVelocityY == 0 && Input.GetMouseButtonDown(0)) {
            rigid2D.AddForce(transform.up * jumpForce);
        }

        if (transform.position.y <= -10f) {
            SceneManager.LoadScene("GameScene");
        }

        if (rigid2D.linearVelocityX < maxWalkSpeed) {
            rigid2D.AddForce(transform.right * walkForce);
        }

        if (rigid2D.linearVelocityY != 0) {
            spriteRenderer.sprite = jumpSprite;
        }
        else {
            time += Time.deltaTime;
            if (time > 0.1f) {
                time = 0;
                spriteRenderer.sprite = walkSprites[idx];
                idx = 1 - idx;
            }
        }
    }

    private void OnTriggerEnter2D(Collider2D collision) {
        Debug.Log("Goal!");
        SceneManager.LoadScene("ClearScene");
    }
}
