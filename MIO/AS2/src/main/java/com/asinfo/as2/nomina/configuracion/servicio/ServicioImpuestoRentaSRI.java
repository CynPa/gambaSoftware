package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.ImpuestoRentaSRI;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioImpuestoRentaSRI
{
  public abstract void guardar(ImpuestoRentaSRI paramImpuestoRentaSRI)
    throws ExcepcionAS2Nomina, AS2Exception, ExcepcionAS2;
  
  public abstract void eliminar(ImpuestoRentaSRI paramImpuestoRentaSRI);
  
  public abstract ImpuestoRentaSRI buscarPorId(int paramInt);
  
  public abstract List<ImpuestoRentaSRI> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ImpuestoRentaSRI> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract ImpuestoRentaSRI cargarDetalle(int paramInt);
  
  public abstract List<ImpuestoRentaSRI> obtenerTablaPorAnio(int paramInt1, int paramInt2);
  
  public abstract void cargarImpuestoRentaSRI(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws ExcepcionAS2, IOException;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioImpuestoRentaSRI
 * JD-Core Version:    0.7.0.1
 */