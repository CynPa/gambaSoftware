package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.entities.Caja;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioCajaRemoto
{
  public abstract void guardar(Caja paramCaja);
  
  public abstract void eliminar(Caja paramCaja);
  
  public abstract Caja buscarPorId(int paramInt);
  
  public abstract List<Caja> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List getReporteCaja(Date paramDate1, Date paramDate2, String paramString, int paramInt);
  
  public abstract Caja cargarDetalle(int paramInt);
  
  public abstract Caja buscarCaja(Map<String, String> paramMap)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioCajaRemoto
 * JD-Core Version:    0.7.0.1
 */