package com.netsocks.associatefan.domain.customer;

import com.netsocks.associatefan.domain.campaign.Campaign;
import com.netsocks.associatefan.domain.campaign.CampaignRepository;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CampaignRepository campaignRepository;

    CustomerService(final CustomerRepository customerRepository, final CampaignRepository campaignRepository) {
        this.customerRepository = customerRepository;
        this.campaignRepository = campaignRepository;
    }

    public Single<Customer> create(final CreateCommand command) {
        return Single.create(emitter -> {
            Customer newCustomer = command.getCustomer();
            Optional<Customer> searchCustomer = customerRepository.findByEmail(newCustomer.getEmail());

            if (searchCustomer.isPresent()) {
                emitter.onError(new EmailAlreadyRegisteredException());
                return;
            }

            List<Campaign> campaigns = campaignRepository.findByTeamId(newCustomer.getFavoriteTeam());

            if (!campaigns.isEmpty()) {
                newCustomer.setCampaigns(campaigns);
            }

            emitter.onSuccess(customerRepository.save(newCustomer));
        });
    }

    public Completable update(final UpdateCommand command) {
        return Completable.create(emitter -> {
            Optional<Customer> customerOptional = customerRepository.findById(command.getId());

            if (!customerOptional.isPresent()) {
                emitter.onError(new CustomerNotFoundException());
                return;
            }

            Customer customer = customerOptional.get();
            List<Campaign> campaigns = campaignRepository.findByTeamId(customer.getFavoriteTeam());

            if (!campaigns.isEmpty()) {
                customer.setCampaigns(campaigns);
            }

            customerRepository.save(customer);
            emitter.onComplete();
        });
    }

    public Single<Customer> getByEmail(final String email) {
        return Single.create(emitter -> {
            Optional<Customer> customerOptional = customerRepository.findByEmail(email);

            if (customerOptional.isPresent()) {
                emitter.onSuccess(customerOptional.get());
                return;
            }

            emitter.onError(new CustomerNotFoundException());
        });
    }

    public Observable<List<Campaign>> findCampaigns(FindCampaignsCommand command) {
        return Observable.create(emitter -> {
            Optional<Customer> customerOptional = customerRepository.findByEmail(command.getEmail());

            if (!customerOptional.isPresent()) {
                emitter.onError(new CustomerNotFoundException());
                return;
            }

            Customer customer = customerOptional.get();

            if (customer.getCampaigns().isEmpty()) {
                emitter.onNext(campaignRepository.findByTeamId(customer.getFavoriteTeam()));
                return;
            }


            List<Campaign> campaigns = campaignRepository.findByIds(String.join(",", customer.getCampaigns()));

            emitter.onNext(campaigns);
        });
    }
}
