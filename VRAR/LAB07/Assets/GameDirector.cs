using UnityEngine;
using UnityEngine.UI;

public class GameDirector : MonoBehaviour
{
    GameObject hpGuage;

    void Start()
    {
        hpGuage = GameObject.Find("hpGauge");
    }

    public void DecreaseHP()
    {
        hpGuage.GetComponent<Image>().fillAmount -= 0.1f;
    }

    void Update()
    {
        
    }
}
