/*   1:    */ package com.asinfo.as2.entities.nomina.asistencia;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Empleado;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import java.util.Date;
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
/*  16:    */ import javax.persistence.Temporal;
/*  17:    */ import javax.persistence.TemporalType;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="marcacion_reloj")
/*  22:    */ public class MarcacionReloj
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="marcacion_reloj", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="marcacion_reloj")
/*  29:    */   @Column(name="id_marcacion_reloj")
/*  30:    */   private int idMarcacionReloj;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_empleado", nullable=true)
/*  37:    */   private Empleado empleado;
/*  38:    */   @Column(name="identificacion", nullable=true, length=20)
/*  39:    */   @Size(min=2, max=20)
/*  40:    */   private String identificacion;
/*  41:    */   @Temporal(TemporalType.DATE)
/*  42:    */   @Column(name="fecha", nullable=false)
/*  43:    */   private Date fecha;
/*  44:    */   @Temporal(TemporalType.TIME)
/*  45:    */   @Column(name="marcacion", nullable=false)
/*  46:    */   private Date marcacion;
/*  47:    */   
/*  48:    */   public int getId()
/*  49:    */   {
/*  50: 78 */     return this.idMarcacionReloj;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public int getIdMarcacionReloj()
/*  54:    */   {
/*  55: 85 */     return this.idMarcacionReloj;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setIdMarcacionReloj(int idMarcacionReloj)
/*  59:    */   {
/*  60: 93 */     this.idMarcacionReloj = idMarcacionReloj;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdOrganizacion()
/*  64:    */   {
/*  65:100 */     return this.idOrganizacion;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdOrganizacion(int idOrganizacion)
/*  69:    */   {
/*  70:108 */     this.idOrganizacion = idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdSucursal()
/*  74:    */   {
/*  75:115 */     return this.idSucursal;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdSucursal(int idSucursal)
/*  79:    */   {
/*  80:123 */     this.idSucursal = idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Empleado getEmpleado()
/*  84:    */   {
/*  85:130 */     return this.empleado;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setEmpleado(Empleado empleado)
/*  89:    */   {
/*  90:138 */     this.empleado = empleado;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getIdentificacion()
/*  94:    */   {
/*  95:145 */     return this.identificacion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIdentificacion(String identificacion)
/*  99:    */   {
/* 100:153 */     this.identificacion = identificacion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Date getFecha()
/* 104:    */   {
/* 105:160 */     return this.fecha;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setFecha(Date fecha)
/* 109:    */   {
/* 110:168 */     this.fecha = fecha;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Date getMarcacion()
/* 114:    */   {
/* 115:175 */     return this.marcacion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setMarcacion(Date marcacion)
/* 119:    */   {
/* 120:183 */     this.marcacion = marcacion;
/* 121:    */   }
/* 122:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.nomina.asistencia.MarcacionReloj
 * JD-Core Version:    0.7.0.1
 */