package com.example.GestionStages.Services;
import com.example.GestionStages.models.Periode;
import com.example.GestionStages.models.User;
import com.example.GestionStages.repositories.PeriodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PeriodeService {

    private final PeriodeRepository periodeRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Environment env;

    @Autowired
    public PeriodeService(PeriodeRepository periodeRepository) {
        this.periodeRepository = periodeRepository;
    }

    public List<Periode> getAllPeriodes() {
        return periodeRepository.findAll();
    }

    public Optional<Periode> getPeriodeById(Long id) {
        return periodeRepository.findById(id);
    }

    public List<Periode> getPeriodesByStageId(Long stageId) {
        return periodeRepository.findByStageId(stageId);
    }

    public List<Periode> getPeriodesByStagiaireUsername(String stagiaireUsername) {
        return periodeRepository.findByStagiaireUsername(stagiaireUsername);
    }

    public List<Periode> getAllActivePeriodes() {
        return periodeRepository.findAllActivePeriodes(LocalDate.now());
    }

    public List<Periode> getPeriodesByTuteurId(String tuteurId) {
        return periodeRepository.findAllByTuteurId(tuteurId);
    }

    public Periode savePeriode(Periode periode) {
        return periodeRepository.save(periode);
    }

    public void deletePeriode(Long id) {
        periodeRepository.deleteById(id);
    }

    public int countAppreciationsByTuteurForPeriode(Long periodeId, String tuteurId) {
        return periodeRepository.countAppreciationsByTuteurForPeriode(periodeId, tuteurId);
    }

    public boolean sendEmail(User user, Periode periode) {
        try {
            String link = env.getProperty("frontend.link")+"/evaluerstage/";
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getUsername());
            message.setSubject("Évaluation du stage");
            message.setText(String.format(
                    "Bonjour %s %s,\n\n" +
                            "Veuillez évaluer ce stage: %s\n\n" +
                            "Cordialement,\n" +
                            "L'équipe de l'application de getion des stages",
                    user.getFirstname(), user.getLastname(),
                    link+periode.getId()
            ));

            emailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
