/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import javax.persistence.GeneratedValue;
/*  10:    */ import javax.persistence.GenerationType;
/*  11:    */ import javax.persistence.Id;
/*  12:    */ import javax.persistence.JoinColumn;
/*  13:    */ import javax.persistence.ManyToOne;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.Max;
/*  17:    */ import javax.validation.constraints.Min;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="detalle_plan_mantenimiento_frecuencia", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_detalle_plan_mantenimiento", "id_frecuencia"})})
/*  22:    */ public class DetallePlanMantenimientoFrecuencia
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="detalle_plan_mantenimiento_frecuencia", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_plan_mantenimiento_frecuencia")
/*  30:    */   @Column(name="id_detalle_plan_mantenimiento_frecuencia")
/*  31:    */   private int idDetallePlanMantenimientoFrecuencia;
/*  32:    */   @Column(name="id_organizacion")
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal")
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="valor", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   @Min(0L)
/*  39:    */   private BigDecimal valor;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_detalle_plan_mantenimiento", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private DetallePlanMantenimiento detallePlanMantenimiento;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_frecuencia", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private Frecuencia frecuencia;
/*  48:    */   @Column(name="porcentaje", nullable=true)
/*  49:    */   @Min(0L)
/*  50:    */   @Max(100L)
/*  51: 67 */   private BigDecimal porcentaje = BigDecimal.ZERO;
/*  52:    */   
/*  53:    */   public int getId()
/*  54:    */   {
/*  55: 82 */     return this.idDetallePlanMantenimientoFrecuencia;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdDetallePlanMantenimientoFrecuencia()
/*  59:    */   {
/*  60: 89 */     return this.idDetallePlanMantenimientoFrecuencia;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdDetallePlanMantenimientoFrecuencia(int idDetallePlanMantenimientoFrecuencia)
/*  64:    */   {
/*  65: 97 */     this.idDetallePlanMantenimientoFrecuencia = idDetallePlanMantenimientoFrecuencia;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdOrganizacion()
/*  69:    */   {
/*  70:104 */     return this.idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdOrganizacion(int idOrganizacion)
/*  74:    */   {
/*  75:112 */     this.idOrganizacion = idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdSucursal()
/*  79:    */   {
/*  80:119 */     return this.idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdSucursal(int idSucursal)
/*  84:    */   {
/*  85:127 */     this.idSucursal = idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public BigDecimal getValor()
/*  89:    */   {
/*  90:134 */     return this.valor;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setValor(BigDecimal valor)
/*  94:    */   {
/*  95:142 */     this.valor = valor;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Frecuencia getFrecuencia()
/*  99:    */   {
/* 100:149 */     return this.frecuencia;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setFrecuencia(Frecuencia frecuencia)
/* 104:    */   {
/* 105:157 */     this.frecuencia = frecuencia;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public DetallePlanMantenimiento getDetallePlanMantenimiento()
/* 109:    */   {
/* 110:161 */     return this.detallePlanMantenimiento;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setDetallePlanMantenimiento(DetallePlanMantenimiento detallePlanMantenimiento)
/* 114:    */   {
/* 115:165 */     this.detallePlanMantenimiento = detallePlanMantenimiento;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public BigDecimal getPorcentaje()
/* 119:    */   {
/* 120:169 */     if (this.porcentaje == null) {
/* 121:170 */       this.porcentaje = BigDecimal.ZERO;
/* 122:    */     }
/* 123:172 */     return this.porcentaje;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setPorcentaje(BigDecimal porcentaje)
/* 127:    */   {
/* 128:176 */     this.porcentaje = porcentaje;
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimientoFrecuencia
 * JD-Core Version:    0.7.0.1
 */