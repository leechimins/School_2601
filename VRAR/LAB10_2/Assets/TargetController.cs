using UnityEngine;

public class TargetController : MonoBehaviour
{
    bool r = true, l = false;
    void Start()
    {
        
    }

    void Update() {        
        if (r && transform.position.x < 10) {
            transform.Translate(0.02f, 0, 0);
        }
        else if (r) {
            r = false;
            l = true;
        }


        if (l && transform.position.x > -10) {
            transform.Translate(-0.02f, 0, 0);
        }
        else if (l) {
            r = true;
            l = false;
        }
    }
}
