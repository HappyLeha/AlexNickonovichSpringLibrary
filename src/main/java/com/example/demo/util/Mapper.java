package com.example.demo.util;

import com.example.demo.dto.TripDto;
import com.example.demo.entity.Trip;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {
    private static ModelMapper modelMapper;
    static {
        modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()

                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(false)

                .setMatchingStrategy(MatchingStrategies.STANDARD);
    }
    public static <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);

    }
    public static <S, T> List<T> mapAll(Collection<? extends S>
                                                sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(e -> map(e, targetClass))
                .collect(Collectors.toList());
    }
    public static TripDto convertTrip (Trip trip) {
        return new TripDto(trip.getId(),trip.getDateTimeFrom(),trip.getDateTimeTo(),trip.getFrom(),
                trip.getTo(),trip.getCountOfPlaces(),trip.getCurrentCountOfPlaces(),trip.getTransport(),
                trip.getCost(),trip.getDriver().getLogin());
    }
    public static List<TripDto> convertTripList (List<Trip> list) {
        List<TripDto> listDto=new ArrayList<>();
        for (Trip x:list) {
            listDto.add(convertTrip(x));
        }
        return listDto;
    }
}
