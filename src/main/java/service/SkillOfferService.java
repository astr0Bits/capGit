package service;


import repository.SkillOfferRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.SkillOffer;
import model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SkillOfferService {
    
    @Autowired
    private SkillOfferRepository skillOfferRepository;
    
    public SkillOffer createSkillOffer(String title, String description, 
                                     SkillOffer.SkillCategory category, User teacher) {
        SkillOffer offer = new SkillOffer();
        offer.setTitle(title);
        offer.setDescription(description);
        offer.setCategory(category);
        offer.setTeacher(teacher);
        offer.setCreatedAt(LocalDateTime.now());
        offer.setAvailable(true);
        
        return skillOfferRepository.save(offer);
    }
    
    public List<SkillOffer> getAllAvailableOffers() {
        return skillOfferRepository.findByAvailable(true);
    }
    
    public List<SkillOffer> getOffersByCategory(SkillOffer.SkillCategory category) {
        return skillOfferRepository.findByCategory(category);
    }
    
    public List<SkillOffer> getTeacherOffers(User teacher) {
        return skillOfferRepository.findByTeacherAndAvailable(teacher, true);
    }

	public Optional<SkillOffer> getSkillOfferById(Long skillOfferId) {
        return skillOfferRepository.findById(skillOfferId);
	}
}