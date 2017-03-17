/*
 * WiFiAnalyzer
 * Copyright (C) 2017  VREM Software Development <VREMSoftwareDevelopment@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.vrem.wifianalyzer.wifi.filter;

import android.support.annotation.NonNull;

import com.vrem.wifianalyzer.settings.Settings;

public class Filters implements Filter {
    private final Settings settings;
    private SSIDFilter SSIDFilter;
    private WiFiBandFilter wiFiBandFilter;
    private StrengthFilter strengthFilter;
    private SecurityFilter securityFilter;

    public Filters(@NonNull Settings settings) {
        this.settings = settings;
        reload();
    }

    @Override
    public boolean isActive() {
        for (Filter filter : getFilters()) {
            if (filter.isActive()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void reset() {
        for (Filter filter : getFilters()) {
            filter.reset();
            filter.save(settings);
        }
    }

    @Override
    public void save(@NonNull Settings settings) {
        for (Filter filter : getFilters()) {
            filter.save(settings);
        }
    }

    void save() {
        save(settings);
    }

    private Filter[] getFilters() {
        return new Filter[]{SSIDFilter, wiFiBandFilter, strengthFilter, securityFilter};
    }

    SSIDFilter getSSIDFilter() {
        return SSIDFilter;
    }

    WiFiBandFilter getWiFiBandFilter() {
        return wiFiBandFilter;
    }

    StrengthFilter getStrengthFilter() {
        return strengthFilter;
    }

    SecurityFilter getSecurityFilter() {
        return securityFilter;
    }

    void reload() {
        this.SSIDFilter = new SSIDFilter(settings.getSSIDFilter());
        this.wiFiBandFilter = new WiFiBandFilter(settings.getWiFiBandFilter());
        this.strengthFilter = new StrengthFilter(settings.getStrengthFilter());
        this.securityFilter = new SecurityFilter(settings.getSecurityFilter());
    }
}