package Implementation.getNextEpisode;

class Series extends Content {
    private List<Season> seasons;

    public Episode getNextEpisode(Episode current) {
        int seasonIdx = current.getSeasonNumber() - 1;
        int episodeIdx = current.getEpisodeNumber() - 1;
        Season currentSeason = seasons.get(seasonIdx);

        // 同季是否有下一集
        if (episodeIdx + 1 < currentSeason.getEpisodes().size()) {
            return currentSeason.getEpisodes().get(episodeIdx + 1);
        }
        // 是否有下一季
        else if (seasonIdx + 1 < seasons.size()) {
            return seasons.get(seasonIdx + 1).getEpisodes().get(0);
        }

        return null; // 完結
    }
}