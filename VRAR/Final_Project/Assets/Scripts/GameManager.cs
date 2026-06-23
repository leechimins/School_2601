using UnityEngine;

public class GameManager : MonoBehaviour
{
    public static GameManager instance;
    public PlayerController player;

    private void Awake()
    {
        instance = this;
    }
}
