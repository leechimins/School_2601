using UnityEngine;

public class JumpWithCC : MonoBehaviour
{
    CharacterController cc;
    public float speed = 10.0f;
    public float gravity = -20.0f;
    public float yVel = 0.0f;
    public float jumpPower = 10.0f;
    int jumpCount = 0;
    public int maxJumpCount = 2;

    void Start()
    {
        cc = GetComponent<CharacterController>();
    }

    void Update()
    {
        float x = Input.GetAxis("Horizontal") * speed;
        float z = Input.GetAxis("Vertical") * speed;
        Vector3 direction = new Vector3(x, 0, z);

        direction = Camera.main.transform.TransformDirection(direction);

        if (cc.collisionFlags == CollisionFlags.Below) {
            yVel = 0;
            jumpCount = 0;
        }

        if (jumpCount < maxJumpCount && Input.GetButtonDown("Jump")) {
            jumpCount++;
            yVel = jumpPower;
        }

        yVel += gravity * Time.deltaTime;
        direction.y = yVel;
        cc.Move(direction * Time.deltaTime);
    }
}
