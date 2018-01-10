/*   1:    */ package com.asinfo.as2.entities.produccion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Temporal;
/*  18:    */ import javax.persistence.TemporalType;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="plan_maestro_produccion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "nombre"})})
/*  24:    */ public class PlanMaestroProduccion
/*  25:    */   extends EntidadBase
/*  26:    */   implements Serializable
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -6054909171994634772L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="plan_maestro_produccion", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="plan_maestro_produccion")
/*  32:    */   @Column(name="id_plan_maestro_produccion")
/*  33:    */   private int idPlanMaestroProduccion;
/*  34:    */   @Column(name="id_organizacion")
/*  35:    */   private int idOrganizacion;
/*  36:    */   @Column(name="id_sucursal")
/*  37:    */   private int idSucursal;
/*  38:    */   @Column(name="nombre", nullable=false, length=100)
/*  39:    */   @NotNull
/*  40:    */   @Size(min=2, max=100)
/*  41:    */   private String nombre;
/*  42:    */   @Column(name="descripcion", length=200, nullable=true)
/*  43:    */   @Size(max=200)
/*  44:    */   private String descripcion;
/*  45:    */   @Column(name="predeterminado", nullable=false)
/*  46:    */   private boolean predeterminado;
/*  47:    */   @Column(name="activo", nullable=false)
/*  48:    */   private boolean activo;
/*  49:    */   @Temporal(TemporalType.DATE)
/*  50:    */   @Column(name="fecha_inicio", nullable=false)
/*  51:    */   @NotNull
/*  52:    */   private Date fechaInicio;
/*  53:    */   @Temporal(TemporalType.DATE)
/*  54:    */   @Column(name="fecha_fin", nullable=false)
/*  55:    */   @NotNull
/*  56:    */   private Date fechaFin;
/*  57:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="planMaestroProduccion")
/*  58: 85 */   private List<DetallePlanMaestroProduccion> listaDetallePlanMaestroProduccion = new ArrayList();
/*  59:    */   
/*  60:    */   public int getId()
/*  61:    */   {
/*  62: 96 */     return this.idPlanMaestroProduccion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdPlanMaestroProduccion()
/*  66:    */   {
/*  67:100 */     return this.idPlanMaestroProduccion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdPlanMaestroProduccion(int idPlanMaestroProduccion)
/*  71:    */   {
/*  72:104 */     this.idPlanMaestroProduccion = idPlanMaestroProduccion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdOrganizacion()
/*  76:    */   {
/*  77:108 */     return this.idOrganizacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdOrganizacion(int idOrganizacion)
/*  81:    */   {
/*  82:112 */     this.idOrganizacion = idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdSucursal()
/*  86:    */   {
/*  87:116 */     return this.idSucursal;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdSucursal(int idSucursal)
/*  91:    */   {
/*  92:120 */     this.idSucursal = idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getNombre()
/*  96:    */   {
/*  97:124 */     return this.nombre;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setNombre(String nombre)
/* 101:    */   {
/* 102:128 */     this.nombre = nombre;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getDescripcion()
/* 106:    */   {
/* 107:132 */     return this.descripcion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setDescripcion(String descripcion)
/* 111:    */   {
/* 112:136 */     this.descripcion = descripcion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public boolean isPredeterminado()
/* 116:    */   {
/* 117:140 */     return this.predeterminado;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setPredeterminado(boolean predeterminado)
/* 121:    */   {
/* 122:144 */     this.predeterminado = predeterminado;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isActivo()
/* 126:    */   {
/* 127:148 */     return this.activo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setActivo(boolean activo)
/* 131:    */   {
/* 132:152 */     this.activo = activo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public Date getFechaInicio()
/* 136:    */   {
/* 137:156 */     return this.fechaInicio;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setFechaInicio(Date fechaInicio)
/* 141:    */   {
/* 142:160 */     this.fechaInicio = fechaInicio;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Date getFechaFin()
/* 146:    */   {
/* 147:164 */     return this.fechaFin;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setFechaFin(Date fechaFin)
/* 151:    */   {
/* 152:168 */     this.fechaFin = fechaFin;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List<DetallePlanMaestroProduccion> getListaDetallePlanMaestroProduccion()
/* 156:    */   {
/* 157:172 */     return this.listaDetallePlanMaestroProduccion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setListaDetallePlanMaestroProduccion(List<DetallePlanMaestroProduccion> listaDetallePlanMaestroProduccion)
/* 161:    */   {
/* 162:177 */     this.listaDetallePlanMaestroProduccion = listaDetallePlanMaestroProduccion;
/* 163:    */   }
/* 164:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.produccion.PlanMaestroProduccion
 * JD-Core Version:    0.7.0.1
 */