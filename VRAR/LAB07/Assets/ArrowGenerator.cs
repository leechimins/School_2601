using UnityEngine;

public class ArrowGenerator : MonoBehaviour
{
    public GameObject arrowPrefab;
    float span = 1.0f;
    float gen = 0;
    float speedTimer = 0;
    float arrowSpeed = 0.1f;

    void Start()
    {
        
    }
    void Update()
    {
        gen += Time.deltaTime;
        speedTimer += Time.deltaTime;

        if (speedTimer > 3.0f) {
            speedTimer = 0;
            arrowSpeed += 0.02f;
        }

        if (gen > span) {
            gen = 0;
            GameObject go = Instantiate(arrowPrefab);
            int px = Random.Range(-6, 7);
            go.transform.position = new Vector3(px, 7, 0);

            go.GetComponent<ArrowController>().speed = arrowSpeed;
        }
    }
}
