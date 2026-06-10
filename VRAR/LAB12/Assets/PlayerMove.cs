using UnityEngine;
using static UnityEditor.PlayerSettings;

public class PlayerMove : MonoBehaviour
{
    Rigidbody rb;
    public float speed = 0.4f;
    public float jumpSpeed = 5f;

    void Start()
    {
        Application.targetFrameRate = 60;
        rb = GetComponent<Rigidbody>();
    }

    void Update()
    {
        move();
        jump();
    }

    void move() {
        float h = Input.GetAxisRaw("Horizontal");
        float v = Input.GetAxisRaw("Vertical");
        Vector3 pos = new Vector3(h, 0, v);

        rb.AddForce(pos * speed, ForceMode.Impulse);
    }

    void jump() {
        // if (Input.GetButtonDown("Jump"))로 대체 가능
        if (Input.GetKeyDown(KeyCode.Space)) {
            rb.AddForce(Vector3.up * jumpSpeed, ForceMode.Impulse);
        }
    }
}
