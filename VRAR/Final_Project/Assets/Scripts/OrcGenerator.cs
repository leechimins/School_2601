using UnityEngine;

public class OrcGenerator : MonoBehaviour
{
    public GameObject orcPrefab;
    public float span;
    float delta;

    float minX = -2.5f;
    float maxX = 2.5f;
    float minY = -4.5f;
    float maxY = 4.5f;
    private float buffer = 1.0f;

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
            SpawnOrc();
        }
    }

    public void SpawnOrc()
    {
        Vector3 spawnPosition = Vector3.zero;
        spawnPosition.z = 0f;
        int side = Random.Range(0, 4);

        switch (side)
        {
            case 0:
                spawnPosition.x = Random.Range(minX, maxX);
                spawnPosition.y = maxY + buffer;
                break;

            case 1:
                spawnPosition.x = Random.Range(minX, maxX);
                spawnPosition.y = minY - buffer;
                break;

            case 2:
                spawnPosition.x = minX - buffer;
                spawnPosition.y = Random.Range(minY, maxY);
                break;

            case 3:
            default:
                spawnPosition.x = maxX + buffer;
                spawnPosition.y = Random.Range(minY, maxY);
                break;
        }

        Instantiate(orcPrefab, spawnPosition, Quaternion.identity);
    }
}
