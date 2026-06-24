using UnityEngine;
using TMPro;

public class GameDirector : MonoBehaviour
{
    public static GameDirector instance;

    [Header("UI Elements")]
    public TextMeshProUGUI timeText;
    public TextMeshProUGUI scoreText;
    public GameObject gameOverPanel;
    public TextMeshProUGUI finalRecordText;

    private float difficultyTimer;

    private void Awake()
    {
        instance = this;
    }

    private void Start()
    {
        difficultyTimer = 0f;
        Time.timeScale = 1.0f;
        gameOverPanel.SetActive(false);
    }

    private void Update()
    {
        if (GameManager.instance.isGameOver) return;

        GameManager.instance.survivalTime += Time.deltaTime;
        difficultyTimer += Time.deltaTime;

        timeText.text = $"Time: {GameManager.instance.survivalTime:F1}s";
        scoreText.text = $"Score: {GameManager.instance.score}";

        if (difficultyTimer >= 10f)
        {
            difficultyTimer = 0f;
            IncreaseDifficulty();
        }
    }

    private void IncreaseDifficulty()
    {
        GameManager.instance.waveLevel++;
        GameManager.instance.orcSpeed += 0.5f;
        GameManager.instance.orcSpawnSpan = Mathf.Max(0.2f, GameManager.instance.orcSpawnSpan - 0.15f);

        Debug.Log($"Wave Level Up! Level: {GameManager.instance.waveLevel}, Orc Speed: {GameManager.instance.orcSpeed}, Spawn Span: {GameManager.instance.orcSpawnSpan}");
    }

    public void DisplayGameOver()
    {
        gameOverPanel.SetActive(true);
        finalRecordText.text = $"Survival Time: {GameManager.instance.survivalTime:F1}s\nScore: {GameManager.instance.score} Kills";
    }
}
