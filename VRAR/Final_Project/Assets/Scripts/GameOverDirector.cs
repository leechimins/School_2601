using UnityEngine;
using TMPro;
using UnityEngine.SceneManagement;

public class GameOverDirector : MonoBehaviour
{
    public TextMeshProUGUI finalRecordText;
    public string gameSceneName = "GameScene";

    void Start()
    {
        if (GameManager.instance != null)
        {
            finalRecordText.text = $"Survival Time: {GameManager.instance.survivalTime:F1}s\nScore: {GameManager.instance.score} Kills";
        }
        else
        {
            finalRecordText.text = "No Game Data Found.";
        }
    }

    public void OnClickRestart()
    {
        if (GameManager.instance != null)
        {
            GameManager.instance.ResetData();
        }
        SceneManager.LoadScene(gameSceneName);
    }
}
