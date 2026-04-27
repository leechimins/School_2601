using UnityEngine;

public class ArrowController : MonoBehaviour
{
    GameObject player;
    float distance, r1 = 1.0f, r2 = 0.5f;
    public float speed = 0.1f;

    void Start()
    {
        player = GameObject.Find("player_0");
    }

    void Update()
    {
        transform.Translate(0, -speed, 0);
        if (transform.position.y < -5) {
            Destroy(gameObject);
        }

        distance = Vector2.Distance(player.transform.position, transform.position);
        // Vector 2 dir = pos1 - pos2; dir.magnitude;µµ °¡´É

        if (distance < r1 + r2) {
            GameObject director = GameObject.Find("GameDirector");
            director.GetComponent<GameDirector>().DecreaseHP();

            Destroy(gameObject);
        }
    }
}
