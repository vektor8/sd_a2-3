package sd.utcn.server.service;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import sd.utcn.server.dto.OrderDto;

import static sd.utcn.server.security.Constants.MAILJET_API_KEY;
import static sd.utcn.server.security.Constants.MAILJET_API_SECRET;


@Slf4j
@Service
public class EmailService {
    /**
     * send an email with data about given order to the given admin address
     * @param order
     * @param adminEmail
     */
    public void sendEmailToAdmin(OrderDto order, String adminEmail) {
        String subject = "New order";
        var total = 0.0;
        for (var i : order.getOrderedFoods()) {
            total += i.getQuantity() * i.getFood().getPrice();
        }

        String message = "Address: " + order.getAddress() + "<br>"
                + "Details: " + order.getOrderDetails() + "<br>" +
                "Order: <br>" +
                String.join("",
                        order.getOrderedFoods()
                                .stream().map(e -> e.getFood().getName() + " x " +
                                        e.getQuantity() + "  " +
                                        e.getFood().getPrice() * e.getQuantity() + " Lei<br>").toList())
                + "Total: " + total;
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;

        client = new MailjetClient(MAILJET_API_KEY, MAILJET_API_SECRET, new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "mvictorandrei@gmail.com")
                                        .put("Name", "Victor-Andrei"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", adminEmail)
                                                .put("Name", "Victor Andrei")))
                                .put(Emailv31.Message.SUBJECT, subject)
                                .put(Emailv31.Message.HTMLPART, message)));
        log.info("sending email from to admin {}", adminEmail);
        try {
            response = client.post(request);
            log.info("email sending status code {}", response.getStatus());
        } catch (MailjetException | MailjetSocketTimeoutException ex) {
            log.info("email sending failed");
        }

    }
    public static void main(String[] args){
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;

        client = new MailjetClient(MAILJET_API_KEY, MAILJET_API_SECRET, new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "mvictorandrei@gmail.com")
                                        .put("Name", "Victor-Andrei"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", "mvictorandrei@gmail.com")
                                                .put("Name", "Victor Andrei")))
                                .put(Emailv31.Message.SUBJECT, "Subiect")
                                .put(Emailv31.Message.HTMLPART, "haha")
                                .put(Emailv31.Message.CUSTOMID, "1231r51")));
        try {
            response = client.post(request);
            System.out.println(response.getStatus());
        } catch (MailjetException | MailjetSocketTimeoutException ex) {
            log.info("email sending failed");
        }
    }
}
