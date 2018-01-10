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
/*  16:    */ import javax.validation.constraints.DecimalMin;
/*  17:    */ import javax.validation.constraints.Digits;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import org.hibernate.annotations.ColumnDefault;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="actividad_responsable_orden_trabajo_mantenimiento", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_responsable_orden_trabajo_mantenimiento", "id_detalle_orden_trabajo_mantenimiento"})})
/*  23:    */ public class ActividadResponsableOrdenTrabajoMantenimiento
/*  24:    */   extends EntidadBase
/*  25:    */   implements Serializable
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @Id
/*  29:    */   @TableGenerator(name="actividad_responsable_orden_trabajo_mantenimiento", initialValue=0, allocationSize=50)
/*  30:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="actividad_responsable_orden_trabajo_mantenimiento")
/*  31:    */   @Column(name="id_actividad_responsable_orden_trabajo_mantenimiento")
/*  32:    */   private int idActividadResponsableOrdenTrabajoMantenimiento;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   @NotNull
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="id_organizacion", nullable=false)
/*  37:    */   @NotNull
/*  38:    */   private int idOrganizacion;
/*  39:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  40:    */   @JoinColumn(name="id_responsable_orden_trabajo_mantenimiento", nullable=false)
/*  41:    */   @NotNull
/*  42:    */   private ResponsableOrdenTrabajoMantenimiento responsableOrdenTrabajoMantenimiento;
/*  43:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  44:    */   @JoinColumn(name="id_detalle_orden_trabajo_mantenimiento", nullable=false)
/*  45:    */   @NotNull
/*  46:    */   private DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento;
/*  47:    */   @Column(name="horas_trabajo", nullable=false, precision=12, scale=2)
/*  48:    */   @Digits(integer=12, fraction=2)
/*  49:    */   @DecimalMin("0.00")
/*  50:    */   @NotNull
/*  51: 58 */   private BigDecimal horasTrabajo = BigDecimal.ZERO;
/*  52:    */   @Column(name="valor_factura", nullable=false, precision=12, scale=2)
/*  53:    */   @Digits(integer=12, fraction=2)
/*  54:    */   @DecimalMin("0.00")
/*  55:    */   @NotNull
/*  56:    */   @ColumnDefault("0")
/*  57: 64 */   private BigDecimal valorFactura = BigDecimal.ZERO;
/*  58:    */   
/*  59:    */   public int getId()
/*  60:    */   {
/*  61: 73 */     return this.idActividadResponsableOrdenTrabajoMantenimiento;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdActividadResponsableOrdenTrabajoMantenimiento()
/*  65:    */   {
/*  66: 77 */     return this.idActividadResponsableOrdenTrabajoMantenimiento;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdActividadResponsableOrdenTrabajoMantenimiento(int idActividadResponsableOrdenTrabajoMantenimiento)
/*  70:    */   {
/*  71: 81 */     this.idActividadResponsableOrdenTrabajoMantenimiento = idActividadResponsableOrdenTrabajoMantenimiento;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdSucursal()
/*  75:    */   {
/*  76: 85 */     return this.idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdSucursal(int idSucursal)
/*  80:    */   {
/*  81: 89 */     this.idSucursal = idSucursal;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getIdOrganizacion()
/*  85:    */   {
/*  86: 93 */     return this.idOrganizacion;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setIdOrganizacion(int idOrganizacion)
/*  90:    */   {
/*  91: 97 */     this.idOrganizacion = idOrganizacion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public ResponsableOrdenTrabajoMantenimiento getResponsableOrdenTrabajoMantenimiento()
/*  95:    */   {
/*  96:101 */     return this.responsableOrdenTrabajoMantenimiento;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setResponsableOrdenTrabajoMantenimiento(ResponsableOrdenTrabajoMantenimiento responsableOrdenTrabajoMantenimiento)
/* 100:    */   {
/* 101:105 */     this.responsableOrdenTrabajoMantenimiento = responsableOrdenTrabajoMantenimiento;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public DetalleOrdenTrabajoMantenimiento getDetalleOrdenTrabajoMantenimiento()
/* 105:    */   {
/* 106:109 */     return this.detalleOrdenTrabajoMantenimiento;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setDetalleOrdenTrabajoMantenimiento(DetalleOrdenTrabajoMantenimiento detalleOrdenTrabajoMantenimiento)
/* 110:    */   {
/* 111:113 */     this.detalleOrdenTrabajoMantenimiento = detalleOrdenTrabajoMantenimiento;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public BigDecimal getHorasTrabajo()
/* 115:    */   {
/* 116:117 */     return this.horasTrabajo;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setHorasTrabajo(BigDecimal horasTrabajo)
/* 120:    */   {
/* 121:121 */     this.horasTrabajo = horasTrabajo;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public BigDecimal getValorFactura()
/* 125:    */   {
/* 126:125 */     return this.valorFactura;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setValorFactura(BigDecimal valorFactura)
/* 130:    */   {
/* 131:129 */     this.valorFactura = valorFactura;
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.ActividadResponsableOrdenTrabajoMantenimiento
 * JD-Core Version:    0.7.0.1
 */