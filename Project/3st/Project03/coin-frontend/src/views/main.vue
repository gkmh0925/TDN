<template>

  <!-- 프로그레스 바 -->
  <div class ="black-bg" :style="'display : ' + progress" >
    <div class="white-bg">
      <div class="droplet_spinner">
        <div class="droplet"></div>
        <div class="droplet"></div>
        <div class="droplet"></div>
      </div>
    </div>
  </div>

  <!-- 티커 테이프 위젯 -->
  <div class="row">
    <div class="col-xl-12 grid-margin stretch-card">
      <div class="card">
        <div class="card-body">
          <TickerTape :options="{
            symbols: [
              {
                description: 'BTC/USDT',
                proName: 'BINANCE:BTCUSDT'
              },
              {
                description: 'ETH/USDT',
                proName: 'BINANCE:ETHUSDT'
              },
              {
                description: 'XRP/USDT',
                proName: 'BINANCE:XRPUSDT'
              },
              {
                description: 'USDT/USD',
                proName: 'COINBASE:USDTUSD'
              },
              {
                description: 'USDCUSD',
                proName: 'GEMINI:USDCUSD'
              }
            ],
            showSymbolLogo: true,
            isTransparent: false,
            displayMode: 'regular',
            colorTheme: 'dark',
            locale: 'kr'
          }" />
        </div>
      </div>
    </div>

    <div class="col-xl-12 grid-margin stretch-card">
      <div class="card">
        <div class="card-body" style="height: 630px">
          <SymbolInfo :options="{
            symbol: 'BINANCE:BTCUSD',
            width: '100%',
            locale: 'en',
            colorTheme: 'dark',
            isTransparent: false
        }"/>

          <Chart :options="{
            autosize: true,
            symbol: 'BINANCE:BTCUSD',
            interval: '240',
            timezone: 'Asia/Seoul',
            theme: 'dark',
            style: '1',
            locale: 'kr',
            enable_publishing: false,
            allow_symbol_change: true,
            save_image: false,
            calendar: false,
            support_host: 'https://www.tradingview.com'
          }" style="height: 70%; width: 100%;"/>

        </div>
      </div>
    </div>

    <div class="col-xl-12 grid-margin stretch-card" >
      <div class="card">
        <div class="card-body" style="width: 100%; height: 300px">
          <CryptoMarket :options="{
          defaultColumn: 'overview',
          screener_type: 'crypto_mkt',
          displayCurrency: 'USD',
          colorTheme: 'dark',
          locale: 'kr',
          }" style="height: 100%; width: 100%"/>
        </div>
      </div>
    </div>

    <div class="col-xl-6 grid-margin stretch-card" >
      <div class="card">
        <div class="card-body" style="width: 100%; height: 600px">
          <TechnicalAnalysis :options="{
            interval: '1m',
            width: '100%',
            isTransparent: false,
            height: 600,
            symbol: 'BINANCE:BTCUSDT',
            showIntervalTabs: true,
            displayMode: 'single',
            locale: 'kr',
            colorTheme: 'dark'
            }" style="height: 85%; width: 100%"/>
        </div>
      </div>
    </div>

    <div class="col-xl-6 grid-margin stretch-card" >
      <div class="card">
        <div class="card-body" style="width: 100%; height: 100%">
          <MarketOverview :options="{
           colorTheme: 'dark',
           dateRange: '12M',
           showChart: true,
           locale: 'en',
           largeChartUrl: '',
           isTransparent: false,
           showSymbolLogo: true,
           showFloatingTooltip: false,
           width: '100%',
           height: 600,
           plotLineColorGrowing: 'rgba(41, 98, 255, 1)',
           plotLineColorFalling: 'rgba(41, 98, 255, 1)',
           gridLineColor: 'rgba(240, 243, 250, 0)',
           scaleFontColor: 'rgba(106, 109, 120, 1)',
           belowLineFillColorGrowing: 'rgba(41, 98, 255, 0.12)',
           belowLineFillColorFalling: 'rgba(41, 98, 255, 0.12)',
           belowLineFillColorGrowingBottom: 'rgba(41, 98, 255, 0)',
           belowLineFillColorFallingBottom: 'rgba(41, 98, 255, 0)',
           symbolActiveColor: 'rgba(41, 98, 255, 0.12)',
           tabs: [
              {
                title: 'Indices',
                symbols: [
                  {
                    s: 'FOREXCOM:SPXUSD',
                    d: 'S&P 500 Index'
                  },
                  {
                    s: 'FOREXCOM:NSXUSD',
                    d: 'US 100 Cash CFD'
                  },
                  {
                    s: 'FOREXCOM:DJI',
                    d: 'Dow Jones Industrial Average Index'
                  },
                  {
                    s: 'INDEX:NKY',
                    d: 'Nikkei 225'
                  },
                  {
                    s: 'INDEX:DEU40',
                    d: 'DAX Index'
                  },
                  {
                    s: 'FOREXCOM:UKXGBP',
                    d: 'FTSE 100 Index'
                  }
                ],
                originalTitle: 'Indices'
              },
              {
                title: 'Futures',
                symbols: [
                  {
                    s: 'CME_MINI:ES1!',
                    d: 'S&P 500'
                  },
                  {
                    s: 'CME:6E1!',
                    d: 'Euro'
                  },
                  {
                    s: 'COMEX:GC1!',
                    d: 'Gold'
                  },
                  {
                    s: 'NYMEX:CL1!',
                    d: 'WTI Crude Oil'
                  },
                  {
                    s: 'NYMEX:NG1!',
                    d: 'Gas'
                  },
                  {
                    s: 'CBOT:ZC1!',
                    d: 'Corn'
                  }
                ],
                originalTitle: 'Futures'
              },
              {
                title: 'Bonds',
                symbols: [
                  {
                    s: 'CBOT:ZB1!',
                    d: 'T-Bond'
                  },
                  {
                    s: 'CBOT:UB1!',
                    d: 'Ultra T-Bond'
                  },
                  {
                    s: 'EUREX:FGBL1!',
                    d: 'Euro Bund'
                  },
                  {
                    s: 'EUREX:FBTP1!',
                    d: 'Euro BTP'
                  },
                  {
                    s: 'EUREX:FGBM1!',
                    d: 'Euro BOBL'
                  }
                ],
                originalTitle: 'Bonds'
              },
              {
                title: 'Forex',
                symbols: [
                  {
                    s: 'FX:EURUSD',
                    d: 'EUR to USD'
                  },
                  {
                    s: 'FX:GBPUSD',
                    d: 'GBP to USD'
                  },
                  {
                    s: 'FX:USDJPY',
                    d: 'USD to JPY'
                  },
                  {
                    s: 'FX:USDCHF',
                    d: 'USD to CHF'
                  },
                  {
                    s: 'FX:AUDUSD',
                    d: 'AUD to USD'
                  },
                  {
                    s: 'FX:USDCAD',
                    d: 'USD to CAD'
                  }
                ],
                originalTitle: 'Forex'
              }
           ]
        }" style="height: 100%; width: 100%"/>
        </div>
      </div>
    </div>

    <div class="col-xl-12 grid-margin stretch-card" >
      <div class="card">
        <div class="card-body" style="width: 100%; height: 100%;">
          <CompanyProfile :options="{
            width: '100%',
            height: '100%',
            isTransparent: false,
            colorTheme: 'dark',
            symbol: 'COINBASE:BTCUSD',
            locale: 'kr'
          }" style="height: 200px; width: 100%;"/>
        </div>
      </div>
    </div>
    <div class="col-xl-12 grid-margin stretch-card">
      <div class="partners" style="font-size: 18px">Partners :<br>
        <img src="https://static.tradingview.com/static/bundles/coinmarketcap.2128d3682aa85ed85c6b.svg">
        <img src="https://static.tradingview.com/static/bundles/bitcoin.be2ce898640de935fe76.svg">
        <img src="https://static.tradingview.com/static/bundles/blockchain.e4c3ea549af8ce50e069.svg">
        <img src="https://static.tradingview.com/static/bundles/coinbase.ad71323a00d406bac047.svg">
        <img src="https://static.tradingview.com/static/bundles/binance.90c39508f6b4e200eb0d.svg">

        <!--
        <img src ="https://bithumbcorp.com/image/pc/ko/common/logo.png" style="height: 20%; width: 15%;">
        <img src="https://static.tradingview.com/static/bundles/twitter.60df1e572e370a2fcdf4.svg">

        -->
      </div>
    </div>
  </div>
</template>

  <script>
    import { TickerTape, SymbolInfo, Chart, CryptoMarket, TechnicalAnalysis, MarketOverview, CompanyProfile, } from 'vue-tradingview-widgets';
    import { defineComponent } from 'vue'
    export default defineComponent({
      name: 'App',
      components: {
        TickerTape,
        SymbolInfo,
        Chart,
        CryptoMarket,
        TechnicalAnalysis,
        MarketOverview,
        CompanyProfile,
      },
      data(){
        return{
          progress : "none",
        }
      }
    });
  </script>

