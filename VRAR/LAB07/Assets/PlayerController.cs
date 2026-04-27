using UnityEngine;

public class PlayerController : MonoBehaviour
{
    public float speed = 5.0f;
    void Start()
    {
        Application.targetFrameRate = 60;        
    }

    void Update()
    {
        if (Input.GetKeyDown(KeyCode.LeftArrow)) {
            transform.Translate(-speed * Time.deltaTime, 0, 0);
        }
        if (Input.GetKeyDown(KeyCode.RightArrow)) {
            transform.Translate(speed * Time.deltaTime, 0, 0);
        }
    }
}
