using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class WallScript : MonoBehaviour
{
    [SerializeField]
    GameObject brickPrefab;
    // Start is called before the first frame update
    public Transform wallPosition;
    private Vector3 wallVector;

    //public Vector3 cubeVector;
    void Start()
    {
        wallVector = wallPosition.position;

        float zOffset;

        /*for (int x = 0; x < 40; x++)
        {
            wallVector.y = wallPosition.position.y;

            int heightAdjuster;

            if (x < 10 || x > 20)
            {
                heightAdjuster = 10 - x;
                if (heightAdjuster < 0)
                {
                    heightAdjuster *= -1;
                }
            }
            else
            {
                heightAdjuster = 0;
            }
            
            for (int y = 0; y < 12 - heightAdjuster ; y++)
            {
                if (y % 2 == 0)
                {
                    zOffset = 0.5f;
                }
                else
                {
                    zOffset = 0;
                }
                for (int z = 0; z < 10; z++)
                {
                    Vector3 position = new Vector3(wallVector.x,wallVector.y,wallVector.z + z + zOffset);
                    Instantiate(brickPrefab, position, Quaternion.identity);
                }
                wallVector.y += 0.5f;
            }
            wallVector.x += 0.5f;
        }*/
        
        for (int x = 0; x < 9; x++)
        {
            wallVector.y = wallPosition.position.y;

            for (int y = 0; y < 12; y++)
            {
                if (y % 2 == 0)
                {
                    zOffset = 0.5f;
                }
                else
                {
                    zOffset = 0;
                }
                for (int z = 0; z < 10; z++)
                {
                    Vector3 position = new Vector3(wallVector.x,wallVector.y,wallVector.z + z + zOffset);
                    Instantiate(brickPrefab, position, Quaternion.identity);
                }
                wallVector.y += 0.5f;
            }
            wallVector.x += 0.5f;
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
