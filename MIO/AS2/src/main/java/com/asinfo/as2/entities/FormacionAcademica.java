/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import javax.persistence.Column;
/*   4:    */ import javax.persistence.Entity;
/*   5:    */ import javax.persistence.GeneratedValue;
/*   6:    */ import javax.persistence.GenerationType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.JoinColumn;
/*   9:    */ import javax.persistence.ManyToOne;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="formacion_academica")
/*  15:    */ public class FormacionAcademica
/*  16:    */   extends EntidadBase
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = -9185428532828891757L;
/*  19:    */   @Id
/*  20:    */   @TableGenerator(name="formacion_academica", initialValue=0, allocationSize=50)
/*  21:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="formacion_academica")
/*  22:    */   @Column(name="id_formacion_academica")
/*  23:    */   private int idFormacionAcademica;
/*  24:    */   @Column(name="id_organizacion", nullable=false)
/*  25:    */   private int idOrganizacion;
/*  26:    */   @Column(name="id_sucursal", nullable=false)
/*  27:    */   private int idSucursal;
/*  28:    */   @ManyToOne
/*  29:    */   @JoinColumn(name="id_institucion_educativa", nullable=false)
/*  30:    */   private InstitucionEducativa institucionEducativa;
/*  31:    */   @ManyToOne
/*  32:    */   @JoinColumn(name="id_empleado", nullable=false)
/*  33:    */   private Empleado empleado;
/*  34:    */   @ManyToOne
/*  35:    */   @JoinColumn(name="id_nivel_instruccion", nullable=false)
/*  36:    */   private NivelInstruccion nivelInstruccion;
/*  37:    */   @Column(name="mes_desde", nullable=true)
/*  38:    */   private int mesDesde;
/*  39:    */   @Column(name="anio_desde", nullable=true)
/*  40:    */   private int anioDesde;
/*  41:    */   @Column(name="mes_hasta", nullable=true)
/*  42:    */   private int mesHasta;
/*  43:    */   @Column(name="anio_hasta", nullable=true)
/*  44:    */   private int anioHasta;
/*  45:    */   
/*  46:    */   public int getId()
/*  47:    */   {
/*  48: 85 */     return getIdFormacionAcademica();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getIdOrganizacion()
/*  52:    */   {
/*  53: 98 */     return this.idOrganizacion;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setIdOrganizacion(int idOrganizacion)
/*  57:    */   {
/*  58:108 */     this.idOrganizacion = idOrganizacion;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdSucursal()
/*  62:    */   {
/*  63:117 */     return this.idSucursal;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdSucursal(int idSucursal)
/*  67:    */   {
/*  68:127 */     this.idSucursal = idSucursal;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public InstitucionEducativa getInstitucionEducativa()
/*  72:    */   {
/*  73:136 */     return this.institucionEducativa;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setInstitucionEducativa(InstitucionEducativa institucionEducativa)
/*  77:    */   {
/*  78:146 */     this.institucionEducativa = institucionEducativa;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Empleado getEmpleado()
/*  82:    */   {
/*  83:155 */     return this.empleado;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setEmpleado(Empleado empleado)
/*  87:    */   {
/*  88:165 */     this.empleado = empleado;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public NivelInstruccion getNivelInstruccion()
/*  92:    */   {
/*  93:174 */     return this.nivelInstruccion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setNivelInstruccion(NivelInstruccion nivelInstruccion)
/*  97:    */   {
/*  98:184 */     this.nivelInstruccion = nivelInstruccion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public int getIdFormacionAcademica()
/* 102:    */   {
/* 103:193 */     return this.idFormacionAcademica;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setIdFormacionAcademica(int idFormacionAcademica)
/* 107:    */   {
/* 108:203 */     this.idFormacionAcademica = idFormacionAcademica;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public int getMesDesde()
/* 112:    */   {
/* 113:212 */     return this.mesDesde;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setMesDesde(int mesDesde)
/* 117:    */   {
/* 118:222 */     this.mesDesde = mesDesde;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getAnioDesde()
/* 122:    */   {
/* 123:231 */     return this.anioDesde;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setAnioDesde(int anioDesde)
/* 127:    */   {
/* 128:241 */     this.anioDesde = anioDesde;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int getMesHasta()
/* 132:    */   {
/* 133:250 */     return this.mesHasta;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setMesHasta(int mesHasta)
/* 137:    */   {
/* 138:260 */     this.mesHasta = mesHasta;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getAnioHasta()
/* 142:    */   {
/* 143:269 */     return this.anioHasta;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setAnioHasta(int anioHasta)
/* 147:    */   {
/* 148:279 */     this.anioHasta = anioHasta;
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.FormacionAcademica
 * JD-Core Version:    0.7.0.1
 */