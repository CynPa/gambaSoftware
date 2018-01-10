/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import com.asinfo.as2.enumeraciones.FrecuenciaFechaEnum;
/*   5:    */ import java.io.Serializable;
/*   6:    */ import java.math.BigDecimal;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EnumType;
/*  10:    */ import javax.persistence.Enumerated;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.validation.constraints.Digits;
/*  20:    */ import javax.validation.constraints.NotNull;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="detalle_componente_equipo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_equipo", "id_componente_equipo"})})
/*  24:    */ public class DetalleComponenteEquipo
/*  25:    */   extends EntidadBase
/*  26:    */   implements Serializable
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="detalle_componente_equipo", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_componente_equipo")
/*  32:    */   @Column(name="id_detalle_componente_equipo")
/*  33:    */   private int idDetalleComponenteEquipo;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   @NotNull
/*  36:    */   private int idSucursal;
/*  37:    */   @Column(name="id_organizacion", nullable=false)
/*  38:    */   @NotNull
/*  39:    */   private int idOrganizacion;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_equipo", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private Equipo equipo;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_componente_equipo", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private ComponenteEquipo componenteEquipo;
/*  48:    */   @Column(name="tiempo_vida", nullable=true)
/*  49:    */   @Digits(integer=12, fraction=2)
/*  50: 57 */   private BigDecimal tiempoVida = BigDecimal.ZERO;
/*  51:    */   @Column(name="periodo_vida_util", nullable=true)
/*  52:    */   @Enumerated(EnumType.ORDINAL)
/*  53:    */   private FrecuenciaFechaEnum periodoVidaUtil;
/*  54:    */   @Column(name="porcentaje_alarma", nullable=true)
/*  55:    */   @Digits(integer=12, fraction=2)
/*  56: 65 */   private BigDecimal porcentajeAlarma = BigDecimal.ZERO;
/*  57:    */   
/*  58:    */   public int getId()
/*  59:    */   {
/*  60: 71 */     return this.idDetalleComponenteEquipo;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdDetalleComponenteEquipo()
/*  64:    */   {
/*  65: 75 */     return this.idDetalleComponenteEquipo;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdDetalleComponenteEquipo(int idDetalleComponenteEquipo)
/*  69:    */   {
/*  70: 79 */     this.idDetalleComponenteEquipo = idDetalleComponenteEquipo;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdSucursal()
/*  74:    */   {
/*  75: 83 */     return this.idSucursal;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdSucursal(int idSucursal)
/*  79:    */   {
/*  80: 87 */     this.idSucursal = idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdOrganizacion()
/*  84:    */   {
/*  85: 91 */     return this.idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdOrganizacion(int idOrganizacion)
/*  89:    */   {
/*  90: 95 */     this.idOrganizacion = idOrganizacion;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Equipo getEquipo()
/*  94:    */   {
/*  95: 99 */     return this.equipo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setEquipo(Equipo equipo)
/*  99:    */   {
/* 100:103 */     this.equipo = equipo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public ComponenteEquipo getComponenteEquipo()
/* 104:    */   {
/* 105:107 */     return this.componenteEquipo;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setComponenteEquipo(ComponenteEquipo componenteEquipo)
/* 109:    */   {
/* 110:111 */     this.componenteEquipo = componenteEquipo;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public BigDecimal getTiempoVida()
/* 114:    */   {
/* 115:115 */     return this.tiempoVida;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setTiempoVida(BigDecimal tiempoVida)
/* 119:    */   {
/* 120:119 */     this.tiempoVida = tiempoVida;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public BigDecimal getPorcentajeAlarma()
/* 124:    */   {
/* 125:123 */     return this.porcentajeAlarma;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setPorcentajeAlarma(BigDecimal porcentajeAlarma)
/* 129:    */   {
/* 130:127 */     this.porcentajeAlarma = porcentajeAlarma;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public FrecuenciaFechaEnum getPeriodoVidaUtil()
/* 134:    */   {
/* 135:131 */     return this.periodoVidaUtil;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setPeriodoVidaUtil(FrecuenciaFechaEnum periodoVidaUtil)
/* 139:    */   {
/* 140:135 */     this.periodoVidaUtil = periodoVidaUtil;
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.DetalleComponenteEquipo
 * JD-Core Version:    0.7.0.1
 */