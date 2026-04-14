using UnityEngine;

public class CarController : MonoBehaviour
{
    public bool isStoped = false;
    bool isStoping = false;
    float speed = 0;
    Vector2 sPos, ePos;

    void Start()
    {
        Application.targetFrameRate = 60;
    }

    void Update()
    {
        if (Input.GetMouseButtonDown(0)) {
            sPos = Input.mousePosition;
        }
        else if (Input.GetMouseButtonUp(0)) {
            ePos = Input.mousePosition;
            float swipeLen = ePos.x - sPos.x;
            speed = swipeLen / 750f;
            GetComponent<AudioSource>().Play();
            isStoping = true;
        }

        transform.Translate(speed, 0, 0);   // Translate는 로컬 좌표계
        speed *= 0.98f;
    }
}
