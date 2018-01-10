/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.OneToMany;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ 
/*  12:    */ @Entity
/*  13:    */ @Table(name="tmp_reporte_general_retencion")
/*  14:    */ public class ReporteGeneralRetencion
/*  15:    */ {
/*  16:    */   @Id
/*  17:    */   @Column(name="id_reporte_general_retencion")
/*  18:    */   private int idReporteGeneralRetencion;
/*  19:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteGeneralRetencionVenta")
/*  20: 37 */   private List<ReporteComprasVentasRetenciones> listaRetencionVenta = new ArrayList();
/*  21:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteRetencionCliente")
/*  22: 40 */   private List<ReporteComprasVentasRetenciones> listaRetencionCliente = new ArrayList();
/*  23:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteGeneralRetencionCompra")
/*  24: 43 */   private List<ReporteComprasVentasRetenciones> listaRetencionCompra = new ArrayList();
/*  25:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteGeneralRetencionExportacion")
/*  26: 46 */   private List<ReporteComprasVentasRetenciones> listaRetencionExportacion = new ArrayList();
/*  27:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteGeneralRetencionAnulado")
/*  28: 49 */   private List<ReporteComprasVentasRetenciones> listaRetencionAnulado = new ArrayList();
/*  29:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteGeneralRetencion")
/*  30: 52 */   private List<ReporteRetencionesResumido> listaReporteRetencionesResumido = new ArrayList();
/*  31:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="reporteGeneralRetencionVentaFisicas")
/*  32: 55 */   private List<ReporteComprasVentasRetenciones> listaDatosReporteVentasFisicas = new ArrayList();
/*  33:    */   
/*  34:    */   public int getIdReporteGeneralRetencion()
/*  35:    */   {
/*  36: 64 */     return this.idReporteGeneralRetencion;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setIdReporteGeneralRetencion(int idReporteGeneralRetencion)
/*  40:    */   {
/*  41: 74 */     this.idReporteGeneralRetencion = idReporteGeneralRetencion;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public List<ReporteComprasVentasRetenciones> getListaRetencionVenta()
/*  45:    */   {
/*  46: 83 */     return this.listaRetencionVenta;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setListaRetencionVenta(List<ReporteComprasVentasRetenciones> listaRetencionVenta)
/*  50:    */   {
/*  51: 93 */     this.listaRetencionVenta = listaRetencionVenta;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<ReporteComprasVentasRetenciones> getListaRetencionCompra()
/*  55:    */   {
/*  56:102 */     return this.listaRetencionCompra;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setListaRetencionCompra(List<ReporteComprasVentasRetenciones> listaRetencionCompra)
/*  60:    */   {
/*  61:112 */     this.listaRetencionCompra = listaRetencionCompra;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public List<ReporteComprasVentasRetenciones> getListaRetencionExportacion()
/*  65:    */   {
/*  66:121 */     return this.listaRetencionExportacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setListaRetencionExportacion(List<ReporteComprasVentasRetenciones> listaRetencionExportacion)
/*  70:    */   {
/*  71:131 */     this.listaRetencionExportacion = listaRetencionExportacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List<ReporteComprasVentasRetenciones> getListaRetencionAnulado()
/*  75:    */   {
/*  76:140 */     return this.listaRetencionAnulado;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setListaRetencionAnulado(List<ReporteComprasVentasRetenciones> listaRetencionAnulado)
/*  80:    */   {
/*  81:150 */     this.listaRetencionAnulado = listaRetencionAnulado;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<ReporteRetencionesResumido> getListaReporteRetencionesResumido()
/*  85:    */   {
/*  86:159 */     return this.listaReporteRetencionesResumido;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setListaReporteRetencionesResumido(List<ReporteRetencionesResumido> listaReporteRetencionesResumido)
/*  90:    */   {
/*  91:169 */     this.listaReporteRetencionesResumido = listaReporteRetencionesResumido;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public List<ReporteComprasVentasRetenciones> getListaRetencionCliente()
/*  95:    */   {
/*  96:173 */     return this.listaRetencionCliente;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setListaRetencionCliente(List<ReporteComprasVentasRetenciones> listaRetencionCliente)
/* 100:    */   {
/* 101:177 */     this.listaRetencionCliente = listaRetencionCliente;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public List<ReporteComprasVentasRetenciones> getListaDatosReporteVentasFisicas()
/* 105:    */   {
/* 106:184 */     return this.listaDatosReporteVentasFisicas;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setListaDatosReporteVentasFisicas(List<ReporteComprasVentasRetenciones> listaDatosReporteVentasFisicas)
/* 110:    */   {
/* 111:191 */     this.listaDatosReporteVentasFisicas = listaDatosReporteVentasFisicas;
/* 112:    */   }
/* 113:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteGeneralRetencion
 * JD-Core Version:    0.7.0.1
 */