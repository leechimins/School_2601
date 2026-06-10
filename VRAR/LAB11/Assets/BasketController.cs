using UnityEngine;

public class BasketController : MonoBehaviour
{
    public AudioClip appleSE;
    public AudioClip bombSE;
    AudioSource aud;
    GameObject director;

    void Start()
    {
        Application.targetFrameRate = 60;
        this.aud = GetComponent<AudioSource>();
        this.director = GameObject.Find("GameDirector");
    }

    void Update()
    {
        if (Input.GetMouseButtonDown(0)) {
            Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
            RaycastHit hit;
            if (Physics.Raycast(ray, out hit, Mathf.Infinity)) {
                float x = Mathf.RoundToInt(hit.point.x);
                float z = Mathf.RoundToInt(hit.point.z);
                transform.position = new Vector3(x, 0, z);
            }
        }                
    }

    private void OnTriggerEnter(Collider other) {
        //Debug.Log("¿‚æ“¥Ÿ!");
        if (other.gameObject.tag == "Apple") {
            Debug.Log("Apple");
            aud.PlayOneShot(appleSE);
            this.director.GetComponent<GameDirector>().SetScore(true);
        }
        else {
            Debug.Log("Bomb");
            aud.PlayOneShot(bombSE);
            this.director.GetComponent<GameDirector>().SetScore(false);
        }
        Destroy(other.gameObject);
    }

}
