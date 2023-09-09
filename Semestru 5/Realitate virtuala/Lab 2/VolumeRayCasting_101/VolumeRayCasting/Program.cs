using System;

namespace VolumeRayCasting
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        static void Main(string[] args)
        {
            using (VolumeRayCasting game = new VolumeRayCasting())
            {
                game.Run();
            }
        }
    }
}

