package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Rendezvous;

import repositories.AnnouncementRepository;

@Service
@Transactional
public class AnnouncementService {

		// Managed repository --------------------------------------------------------------------
		@Autowired
		private AnnouncementRepository announcementRepository;

		// Supporting services ------------------------------------------------------------------

		// Constructors ---------------------------------------------------------
		public AnnouncementService() {
			super();
		}

		// CRUD methods ---------------------------------------------------------

		// Other business methods ------------------------------------------------------------
		
		public Double[] avgSqrtAnnouncementsPerRendezvous(){
			Double[] result;
			
			result = this.announcementRepository.avgSqrtAnnouncementsPerRendezvous();
			
			return result;
		}
		
		/*public Collection<Rendezvous> rendezvousesWhoseMoreThat75Announcements(){
			Collection<Rendezvous> result;
			
			result = this.announcementRepository.rendezvousesWhoseMoreThat75Announcements();
			
			return result;
		}*/
		
}
