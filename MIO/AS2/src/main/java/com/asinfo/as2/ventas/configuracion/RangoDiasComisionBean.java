/*   1:    */ package com.asinfo.as2.ventas.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.RangoDiasComision;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.model.LazyDataModel;
/*  20:    */ import org.primefaces.model.SortOrder;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class RangoDiasComisionBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioGenerico<RangoDiasComision> servicioRangoDiasComision;
/*  30:    */   private RangoDiasComision rangoDiasComision;
/*  31:    */   private LazyDataModel<RangoDiasComision> listaRangoDiasComision;
/*  32:    */   private DataTable dtRangoDiasComision;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 57 */     this.listaRangoDiasComision = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = -1956844755215090557L;
/*  40:    */       
/*  41:    */       public List<RangoDiasComision> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 64 */         List<RangoDiasComision> lista = new ArrayList();
/*  44: 65 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 67 */         lista = RangoDiasComisionBean.this.servicioRangoDiasComision.obtenerListaPorPagina(RangoDiasComision.class, startIndex, pageSize, sortField, ordenar, filters);
/*  47:    */         
/*  48: 69 */         RangoDiasComisionBean.this.listaRangoDiasComision.setRowCount(RangoDiasComisionBean.this.servicioRangoDiasComision.contarPorCriterio(RangoDiasComision.class, filters));
/*  49: 70 */         return lista;
/*  50:    */       }
/*  51:    */     };
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String editar()
/*  55:    */   {
/*  56: 82 */     if ((getRangoDiasComision() != null) && (getRangoDiasComision().getId() != 0))
/*  57:    */     {
/*  58: 83 */       this.rangoDiasComision = ((RangoDiasComision)this.servicioRangoDiasComision.buscarPorId(RangoDiasComision.class, Integer.valueOf(this.rangoDiasComision.getId())));
/*  59: 84 */       setEditado(true);
/*  60:    */     }
/*  61:    */     else
/*  62:    */     {
/*  63: 86 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  64:    */     }
/*  65: 88 */     return "";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String guardar()
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72:100 */       if (this.rangoDiasComision.getDiaFinal() < this.rangoDiasComision.getDiaInicial())
/*  73:    */       {
/*  74:101 */         addErrorMessage(getLanguageController().getMensaje("msg_error_rango_de_fechas") + this.rangoDiasComision.getDiaInicial() + " > " + this.rangoDiasComision
/*  75:102 */           .getDiaFinal());
/*  76:103 */         return "";
/*  77:    */       }
/*  78:105 */       this.servicioRangoDiasComision.guardar(this.rangoDiasComision);
/*  79:106 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  80:107 */       limpiar();
/*  81:    */     }
/*  82:    */     catch (Exception e)
/*  83:    */     {
/*  84:109 */       e.printStackTrace();
/*  85:110 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  86:111 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  87:    */     }
/*  88:113 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String eliminar()
/*  92:    */   {
/*  93:123 */     if ((getRangoDiasComision() != null) && (getRangoDiasComision().getId() != 0)) {
/*  94:    */       try
/*  95:    */       {
/*  96:125 */         this.servicioRangoDiasComision.eliminar(this.rangoDiasComision);
/*  97:126 */         addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  98:127 */         limpiar();
/*  99:    */       }
/* 100:    */       catch (Exception e)
/* 101:    */       {
/* 102:129 */         e.printStackTrace();
/* 103:130 */         LOG.error("ERROR AL GUARDAR DATOS", e);
/* 104:131 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 105:    */       }
/* 106:    */     } else {
/* 107:134 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 108:    */     }
/* 109:136 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String limpiar()
/* 113:    */   {
/* 114:146 */     setEditado(false);
/* 115:    */     
/* 116:148 */     this.rangoDiasComision = new RangoDiasComision();
/* 117:149 */     this.rangoDiasComision.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 118:150 */     this.rangoDiasComision.setIdSucursal(AppUtil.getSucursal().getId());
/* 119:151 */     this.rangoDiasComision.setActivo(true);
/* 120:    */     
/* 121:153 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String cargarDatos()
/* 125:    */   {
/* 126:163 */     return "";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public RangoDiasComision getRangoDiasComision()
/* 130:    */   {
/* 131:167 */     return this.rangoDiasComision;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setRangoDiasComision(RangoDiasComision rangoDiasComision)
/* 135:    */   {
/* 136:171 */     this.rangoDiasComision = rangoDiasComision;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public LazyDataModel<RangoDiasComision> getListaRangoDiasComision()
/* 140:    */   {
/* 141:175 */     return this.listaRangoDiasComision;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setListaRangoDiasComision(LazyDataModel<RangoDiasComision> listaRangoDiasComision)
/* 145:    */   {
/* 146:179 */     this.listaRangoDiasComision = listaRangoDiasComision;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public DataTable getDtRangoDiasComision()
/* 150:    */   {
/* 151:183 */     return this.dtRangoDiasComision;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setDtRangoDiasComision(DataTable dtRangoDiasComision)
/* 155:    */   {
/* 156:187 */     this.dtRangoDiasComision = dtRangoDiasComision;
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.RangoDiasComisionBean
 * JD-Core Version:    0.7.0.1
 */