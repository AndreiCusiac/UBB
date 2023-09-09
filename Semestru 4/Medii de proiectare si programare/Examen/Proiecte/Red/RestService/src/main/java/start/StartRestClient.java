package start;

import models.Match;
import org.springframework.web.client.RestClientException;
import rest.client.PlayerClient;

public class StartRestClient {
    private final static PlayerClient matchesClient = new PlayerClient();
    public static void main(String[] args) {
        //  RestTemplate restTemplate=new RestTemplate();
        Match matchT = new Match("testHome", "testAway", 20.0, 100, 80);
        try{
            //  User result= restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class);

            //  System.out.println("Result received "+result);
      /*  System.out.println("Updating  user ..."+userT);
        userT.setName("New name 2");
        restTemplate.put("http://localhost:8080/chat/users/test124", userT);

*/
            // System.out.println(restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class));
            //System.out.println( restTemplate.postForObject("http://localhost:8080/chat/users",userT, User.class));

            show(()-> {
                try {
                    System.out.println(matchesClient.create(matchT));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            show(()->{
                Match[] res= new Match[0];
                try {
                    res = matchesClient.getAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for(Match u:res){
                    System.out.println(u.getId()+": "+u.getHomeTeam());
                }
            });
        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }

        show(()-> {
            try {
                System.out.println(matchesClient.getById("1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch (Exception e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}
