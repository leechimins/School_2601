using UnityEngine;
using UnityEngine.SceneManagement;

public class Clear2Game : MonoBehaviour
{
    void Start()
    {
        
    }

    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Space)) {
            SceneManager.LoadScene("GameScene");
        }
    }
}
