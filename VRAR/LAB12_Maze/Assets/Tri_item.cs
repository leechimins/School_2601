using UnityEngine;

public class Tri_item : MonoBehaviour
{
    public int itemCount;

    private void OnTriggerEnter(Collider other) {
        if (other.tag == "item") {
            itemCount++;
            other.gameObject.SetActive(false);
            Debug.Log("æ∆¿Ã≈€: " + itemCount);
        }
    }
}
