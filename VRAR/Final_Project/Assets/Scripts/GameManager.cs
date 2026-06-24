using UnityEngine;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour
{
    public static GameManager instance;
    public PlayerController player;

    public int score = 0;
    public float survivalTime = 0f;
    public bool isGameOver = false;

    public float orcSpeed = 2.0f;
    public float orcSpawnSpan = 1.0f;
    public int waveLevel = 1;

    private void Awake()
    {
        if (instance == null)
        {
            instance = this;
            DontDestroyOnLoad(gameObject);
        }
        else
        {
            Destroy(gameObject);
            return;
        }

        ResetData();
    }

    public void ResetData()
    {
        score = 0;
        survivalTime = 0f;
        isGameOver = false;

        orcSpeed = 2.0f;
        orcSpawnSpan = 1.0f;
        waveLevel = 1;

        Time.timeScale = 1.0f;
    }

    public void AddScore(int amount)
    {
        if (isGameOver) return;
        score += amount;
    }

    public void GameOver()
    {
        if (isGameOver) return;
        isGameOver = true;

        Time.timeScale = 0f;
        SceneManager.LoadScene("GameOverScene"); // 게임오버 씬 로드
    }
}
