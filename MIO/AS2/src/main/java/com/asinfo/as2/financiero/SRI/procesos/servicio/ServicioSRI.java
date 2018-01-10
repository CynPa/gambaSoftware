package com.asinfo.as2.financiero.SRI.procesos.servicio;

import com.asinfo.as2.entities.TipoIdentificacion;
import com.asinfo.as2.entities.sri.CreditoTributarioSRI;
import com.asinfo.as2.entities.sri.TipoComprobanteSRI;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioSRI
{
  @Deprecated
  public abstract List<CreditoTributarioSRI> obtenerCreditoTributarioSRI();
  
  public abstract List<TipoComprobanteSRI> obtenerTipoComprobanteSRI();
  
  public abstract TipoComprobanteSRI buscarTipoComprobanteSRIPorCodigo(String paramString)
    throws ExcepcionAS2;
  
  public abstract TipoComprobanteSRI buscarTipoComprobanteSRIPorId(int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<TipoComprobanteSRI> buscarPorTipoIdentificacion(TipoIdentificacion paramTipoIdentificacion);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioSRI
 * JD-Core Version:    0.7.0.1
 */