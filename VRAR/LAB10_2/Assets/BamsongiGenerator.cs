using UnityEngine;

public class BamsongiGenerator : MonoBehaviour
{
    public GameObject bamsongiPrefab;
    public float strength = 0f;
    float timer = 0f;
    void Start()
    {
        
    }

    void Update()
    {
        if (Input.GetMouseButton(0)) {
            timer += 100;
        }
        if (Input.GetMouseButtonUp(0)) {
            GameObject bamsongi = Instantiate(bamsongiPrefab);
            Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
            strength = timer;
            bamsongi.GetComponent<BamsongiController>().Shoot(ray.direction * strength);
            Destroy(bamsongi, 8);
            timer = 0;
        }
    }
}
