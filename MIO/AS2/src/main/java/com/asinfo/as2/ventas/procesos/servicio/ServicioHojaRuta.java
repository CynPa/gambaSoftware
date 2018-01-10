package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.DetalleDespachoCliente;
import com.asinfo.as2.entities.DetalleHojaRuta;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.HojaRuta;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.Transportista;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioHojaRuta
{
  public abstract void guardar(HojaRuta paramHojaRuta)
    throws ExcepcionAS2;
  
  public abstract HojaRuta buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract HojaRuta cargarDetalle(Integer paramInteger);
  
  public abstract List<HojaRuta> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List getReporteHojaRuta(int paramInt)
    throws ExcepcionAS2;
  
  public abstract void esEditable(HojaRuta paramHojaRuta)
    throws ExcepcionAS2Financiero, ExcepcionAS2Ventas;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<DetalleDespachoCliente> cargarDespachos(int paramInt, Date paramDate1, Date paramDate2);
  
  public abstract List<DetalleHojaRuta> cargarDetalleHojaRuta(int paramInt, Sucursal paramSucursal, HojaRuta paramHojaRuta, Date paramDate1, Date paramDate2);
  
  public abstract List<DetalleHojaRuta> detalleHojaRutaTransportista(int paramInt, Sucursal paramSucursal, Transportista paramTransportista, Date paramDate1, Empresa paramEmpresa, Date paramDate2);
  
  public abstract List<Object[]> reporteHojaRuta(int paramInt1, int paramInt2);
  
  public abstract List<Object[]> reporteHojaRutaTransportista(int paramInt1, int paramInt2, boolean paramBoolean);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioHojaRuta
 * JD-Core Version:    0.7.0.1
 */