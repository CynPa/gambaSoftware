package com.asinfo.as2.financiero.contabilidad.procesos.servicio;

import com.asinfo.as2.entities.Periodo;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPeriodo
{
  public abstract void guardar(Periodo paramPeriodo);
  
  public abstract void eliminar(Periodo paramPeriodo);
  
  public abstract Periodo buscarPorId(Integer paramInteger);
  
  public abstract Periodo buscarPorFecha(Date paramDate, int paramInt, DocumentoBase paramDocumentoBase)
    throws ExcepcionAS2Financiero;
  
  public abstract Periodo obtenerPeriodoActual(int paramInt, DocumentoBase paramDocumentoBase)
    throws ExcepcionAS2Financiero;
  
  public abstract void validar(Date paramDate1, Date paramDate2, int paramInt)
    throws ExcepcionAS2Financiero;
  
  public abstract List<Periodo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo
 * JD-Core Version:    0.7.0.1
 */