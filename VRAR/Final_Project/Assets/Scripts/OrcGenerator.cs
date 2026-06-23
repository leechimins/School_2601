using UnityEngine;

public class OrcGenerator : MonoBehaviour
{
    public GameObject orcPrefab;
    public float span;
    float delta;

    void Start()
    {
        span = 1.0f;
        delta = 0;
    }

    void FixedUpdate()
    {
        delta += Time.fixedDeltaTime;
        if (delta > span)
        {
            delta = 0;
            Instantiate(orcPrefab);
        }
    }
}
